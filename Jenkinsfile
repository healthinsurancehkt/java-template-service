podTemplate(
    containers: [
        containerTemplate(
            name: 'maven',
            image: 'maven:3.8.1-adoptopenjdk-11',
            command: 'sleep',
            args: '30d'
        ),
        containerTemplate(
            name: 'docker',
            image: 'docker',
            command: 'cat',
            ttyEnabled: true
        )
    ],
    volumes: [
        hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock'),
        persistentVolumeClaim(mountPath: '/root/.m2/repository', claimName: 'maven-repo', readOnly: false)
    ]) {

        node(POD_LABEL) {
            // gitVars will contain the following keys:
            // GIT_BRANCH, GIT_COMMIT, GIT_LOCAL_BRANCH, GIT_PREVIOUS_COMMIT, GIT_PREVIOUS_SUCCESSFUL_COMMIT, GIT_URL
            def gitVars = checkout scm

            stage('Pre-Checks') {
                result = sh (script: "git log -1 | grep '.*\\[jenkins\\-automation\\].*'", returnStatus: true)
                if (result == 0) {
                    echo ("'[jenkins-automation]' spotted in git commit. Aborting.")
                    sh """
                    echo "'[jenkins-automation]' spotted in git commit. Aborting." >> pipeline.output.txt
                    """
                    success ("'[jenkins-automation]' spotted in git commit. Aborting.")
                }
            }

            def branchName = gitVars.GIT_BRANCH

            if(gitVars.GIT_PREVIOUS_COMMIT != gitVars.GIT_COMMIT) {

                //def buildingToProd = branchName == 'origin/master'
                //def buildingToDev = branchName == 'origin/develop'
                def buildingToProd = false
                def buildingToDev = branchName == 'origin/develop'

                if(buildingToProd) {
                    sh """
                    echo "Building to production" >> pipeline.output.txt
                    """
                } else {
                    sh """
                    echo "Building to development" >> pipeline.output.txt
                    """
                }


                // send vars to containers' environment variables
                gitVars.each { k, v -> env[k] = v }
                env["DOCKER_TAG"] = buildingToProd ? "prod-${gitVars.GIT_COMMIT.substring(0, 11)}" : "dev-${gitVars.GIT_COMMIT.substring(0, 11)}"

                echo "GIT BRANCH: ${gitVars.GIT_BRANCH}"
                echo "GIT COMMIT: ${gitVars.GIT_COMMIT}"
                echo "TRUNCATED_COMMIT: ${gitVars.GIT_COMMIT.substring(0, 11)}"

                sh """
                echo "DOCKER_TAG=${DOCKER_TAG}" >> pipeline.output.txt
                echo "DOCKER_TAG=${DOCKER_TAG}" >> ${DOCKER_TAG}.built-version
                """

                container('maven') {

                    stage('Maven build') {
                        sh 'env | grep GIT_'
                        sh 'env | grep DOCKER_'
                        sh 'mvn -B -DskipTests -ntp clean package'
                    }
                }

                if(buildingToProd || buildingToDev) {
                    env["GCP_PROJECT"] = buildingToProd ? "steadfast-leaf-390318" : "steadfast-leaf-390318"
                    def gcrCredentials = buildingToProd ? 'gcr:steadfast-leaf-390318' : 'gcr:steadfast-leaf-390318'

                    sh """
                    echo "GCP_PROJECT=${GCP_PROJECT}" >> pipeline.output.txt
                    """

                    stage('Docker Build') {
                        container('docker') {
                            sh 'env | grep GIT_'
                            sh 'env | grep DOCKER_'
                            sh 'echo GIT_COMMIT = ${GIT_COMMIT}'
                            sh 'echo DOCKER_TAG = ${DOCKER_TAG}'
                            sh 'docker build . -t us-east1-docker.pkg.dev/${GCP_PROJECT}/insurance-now/java-template-service:${DOCKER_TAG}'
                            sh 'docker tag us-east1-docker.pkg.dev/${GCP_PROJECT}/insurance-now/java-template-service:${DOCKER_TAG} us-east1-docker.pkg.dev/${GCP_PROJECT}/insurance-now/java-template-service:latest'
                        }
                    }
                    stage('Docker Push') {
                        container('docker') {
                            docker.withRegistry('https://gcr.io', gcrCredentials) {
                                sh 'env | grep GIT_'
                                sh 'env | grep DOCKER_'
                                sh 'echo GIT_COMMIT = ${GIT_COMMIT}'
                                sh 'echo DOCKER_TAG = ${DOCKER_TAG}'
                                sh 'docker push us-east1-docker.pkg.dev/${GCP_PROJECT}/insurance-now/java-template-service:${DOCKER_TAG}'
                                sh 'docker push us-east1-docker.pkg.dev/${GCP_PROJECT}/insurance-now/java-template-service:latest'
                            }
                        }
                    }
                    if (buildingToDev) {
                        stage('GitOps Update') {
                            env['SERVICE_ENV_NAME'] = buildingToProd ? 'prod' : 'dev'
                            env['SERVICE_ENV_REGEX'] = buildingToProd ? 'prod-.*' : 'dev-.*'
                            env['SERVICE_ENV_COMPATIBILITY_REGEX'] = buildingToProd ? 'prod-.*' : 'stg-.*'
                            env['SERVICE_ENV_LATEST'] = 'latest'

                            sh """
                            echo "SERVICE_ENV_NAME=${SERVICE_ENV_NAME}" >> pipeline.output.txt
                            echo "SERVICE_ENV_REGEX=${SERVICE_ENV_REGEX}" >> pipeline.output.txt
                            echo "SERVICE_ENV_COMPATIBILITY_REGEX=${SERVICE_ENV_COMPATIBILITY_REGEX}" >> pipeline.output.txt
                            echo "SERVICE_ENV_LATEST=${SERVICE_ENV_LATEST}" >> pipeline.output.txt
                            """

                            dir('helm-charts') {
                                git credentialsId: 'github-ssh-login',
                                    url: 'ssh://git@github.com:healthinsurancehkt/helm-charts.git'

                                sh 'sed -i -r "s/${SERVICE_ENV_REGEX}/${DOCKER_TAG}/g" java-template-service/${SERVICE_ENV_NAME}/values.yaml'
                                sh 'sed -i -r "s/${SERVICE_ENV_COMPATIBILITY_REGEX}/${DOCKER_TAG}/g" java-template-service/${SERVICE_ENV_NAME}/values.yaml'
                                sh 'sed -i -r "s/${SERVICE_ENV_LATEST}/${DOCKER_TAG}/g" java-template-service/${SERVICE_ENV_NAME}/values.yaml'
                                sh "git -c user.name='Jenkins Automation' -c user.email=romero.pereira@levio.ca commit -m '[java-template-service] [jenkins-automation] Changing image tag to ${DOCKER_TAG}' -a"
                                withCredentials(
                                    [
                                        sshUserPrivateKey(credentialsId: 'github-ssh-login', keyFileVariable: 'SSH_KEY')
                                    ]
                                ) {
                                   sh """
                                   export GIT_SSH_COMMAND="ssh -i $SSH_KEY"
                                   git push origin main:main --force
                                   """
                                }
                            }
                        }
                    }
                    if(buildingToProd) {
                        archiveArtifacts artifacts: '**/*kpi*.jar,**/java-template-service/prod/values.yaml,**/pipeline.output.txt,**/*.built-version',
                                         onlyIfSuccessful: true,
                                         fingerprint: true,
                                         allowEmptyArchive: true
                    } else {
                        archiveArtifacts artifacts: '**/*kpi*.jar,**/java-template-service/dev/values.yaml,**/pipeline.output.txt,**/*.built-version',
                                         onlyIfSuccessful: true,
                                         fingerprint: true,
                                         allowEmptyArchive: true
                    }
                }
            }
        }
    }