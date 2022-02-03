# Spring Boot ElasticSearch Demo

## Test

Search

```shell
$ curl http://localhost:8080/search/java | fx
```

Search by Article

```shell
$ curl http://localhost:8080/search/article/java | fx
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
    }'
  minikube:9200/sbblogposts/_search
```