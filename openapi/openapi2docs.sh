VERSION=4.0.1
IMAGE=levio/widdershins:$VERSION

if [ -z "$(docker images -q $IMAGE)" ]; then
  rm tmp_dockerfile
  echo 'FROM library/node:slim' > ./tmp_dockerfile
  echo 'ARG VERSION' >> ./tmp_dockerfile
  echo 'RUN npm install -g widdershins@$VERSION' >> ./tmp_dockerfile
  echo 'ENTRYPOINT ["/usr/local/bin/widdershins"]' >> ./tmp_dockerfile

  docker build . -f tmp_dockerfile --build-arg VERSION=$VERSION -t $IMAGE
  rm tmp_dockerfile
fi

docker run --rm -v "${PWD}:/local" $IMAGE --lang --summary --omitHeader --expandBody /local/openapi/openapi.yaml -o /local/openapi/openapi.md

