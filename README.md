# Spring Boot ElasticSearch Demo

- [] Config (`SearchConfig`)
- [] Domain
- [] Repository
- [] Service
- [] Controller
- [] Demo API

## Demo API 

Search

```shell
$ curl http://localhost:8080/search/?searchString=java | fx
$ curl http://localhost:8080/search/?searchString=java&page=0&max=5 | fx
$ curl http://localhost:8080/search/?searchString=%22cloud%20deck%22 | fx
```

Search by Article

```shell
$ curl http://localhost:8080/search/article/?article=java | fx
$ curl http://localhost:8080/search/article/?article=java&page=0&max=5 | fx
```

## Using REST APIs

### Query

All docs:

```shell
$ curl -X POST \
  -H "Content-Type: application/json" \
  --data-raw '{
      "query": {
         "query_string": {
         "query": "java AND aq"
      }
    }
  }' \
  docker.local:9200/sbblogposts/_search | fx
```