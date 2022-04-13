# Spring Boot ElasticSearch Demo

- [ ] Config (`SearchConfig`)
- [ ] Domain (`BlogPost`)
- [ ] Repository (`BlogPostSearchRepository`)
- [ ] Service (`BlogPostService`)
- [ ] Controller (`SearchController`)
- [ ] Application (`SearchDemoApplication`)
- [ ] Demo API

## Demo API 

### Create Blog Post

```shell
POST_ID=$(curl -X POST \
  -H 'Content-Type: application/json' \
  --data-raw  '
    {
      "title": "A story about friends",
      "description": "A tale as old as time...",
      "article": "It is truly serendipitous that we met today."
    }
  ' \
  http://localhost:8080/blogPost | jq -r .blogPost.id)
```

```shell
curl http://localhost:8080/search/?searchString=serendipitous | fx
```

### Update Blog Post

```shell
curl -X PUT \
  -H 'Content-Type: application/json' \
  --data-raw  '
    {
      "id": "'"$POST_ID"'", 
      "title": "A story about friends",
      "description": "A tale as old as time...",
      "article": "It is truly dumb luck that we met today."
    }
  ' \
  http://localhost:8080/blogPost | jq
```

```shell
curl http://localhost:8080/search/?searchString=serendipitous | fx
curl http://localhost:8080/search/?searchString=dumb%20luck | fx
```

### Delete Blog Post

```shell
curl -X DELETE http://localhost:8080/blogPost?id=$POST_ID
```

```shell
curl http://localhost:8080/search/?searchString=dumb%20luck | fx
```

### Search

```shell
curl http://localhost:8080/search/?searchString=java | fx
curl http://localhost:8080/search/?searchString=java&page=0&max=5 | fx
curl http://localhost:8080/search/?searchString=%22cloud%20deck%22 | fx
```

Search by Article

```shell
curl http://localhost:8080/search/article/?article=java | fx
curl http://localhost:8080/search/article/?article=java&page=0&max=5 | fx
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