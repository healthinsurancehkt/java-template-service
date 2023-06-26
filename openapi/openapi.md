<!-- Generator: Widdershins v4.0.1 -->

<h1 id="api-for-javatemplateserviceapplication">API for JavaTemplateServiceApplication v1</h1>

> Scroll down for example requests and responses.

Demo API

<h1 id="api-for-javatemplateserviceapplication-employee-dashboard">Employee Dashboard</h1>

## Create new Employee

<a id="opIdcreateEmployee"></a>

> Code samples

`POST /v1/employee/create`

> Body parameter

<h3 id="create-new-employee-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[Employee](#schemaemployee)|true|none|
|» id|body|[integer_int64](#schemainteger_int64)(int64)|false|none|
|» name|body|string|false|none|

> Example responses

> 200 Response

<h3 id="create-new-employee-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Default response|[Employee](#schemaemployee)|

<aside class="success">
This operation does not require authentication
</aside>

## Fetch all Employee Data

<a id="opIdgetAllEmployees"></a>

> Code samples

`GET /v1/employee/findAll`

> Example responses

> 200 Response

<h3 id="fetch-all-employee-data-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Default response|Inline|

<h3 id="fetch-all-employee-data-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[Employee](#schemaemployee)]|false|none|none|
|» id|[integer_int64](#schemainteger_int64)(int64)|false|none|none|
|» name|string|false|none|none|

<aside class="success">
This operation does not require authentication
</aside>

## Fetch Employee data by ID

<a id="opIdgetEmployeeById0"></a>

> Code samples

`GET /v1/employee/findById/{id}`

<h3 id="fetch-employee-data-by-id-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|[integer_int64](#schemainteger_int64)|true|none|

> Example responses

> 200 Response

<h3 id="fetch-employee-data-by-id-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Default response|[Employee](#schemaemployee)|

<aside class="success">
This operation does not require authentication
</aside>

<h1 id="api-for-javatemplateserviceapplication-greet-controller">Greet Controller</h1>

## Get a greet

<a id="opIdgetEmployeeById1"></a>

> Code samples

`GET /v1/greet`

> Example responses

> 200 Response

<h3 id="get-a-greet-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Default response|string|

<aside class="success">
This operation does not require authentication
</aside>

# Schemas

<h2 id="tocS_integer_int64">integer_int64</h2>
<!-- backwards compatibility -->
<a id="schemainteger_int64"></a>
<a id="schema_integer_int64"></a>
<a id="tocSinteger_int64"></a>
<a id="tocsinteger_int64"></a>

```json
0

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|integer(int64)|false|none|none|

<h2 id="tocS_Employee">Employee</h2>
<!-- backwards compatibility -->
<a id="schemaemployee"></a>
<a id="schema_Employee"></a>
<a id="tocSemployee"></a>
<a id="tocsemployee"></a>

```json
{
  "id": 0,
  "name": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|[integer_int64](#schemainteger_int64)|false|none|none|
|name|string|false|none|none|

