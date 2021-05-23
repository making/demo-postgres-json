```
docker run --rm \
 -p 5432:5432 \
 -e POSTGRES_DB=demo \
 -e POSTGRES_USER=demo \
 -e POSTGRES_PASSWORD=demo \
 bitnami/postgresql:11.11.0-debian-10-r59
 ```


```
curl -s -H "Content-Type: application/json" -w '\n' localhost:8080/data -d '{"role":"author", "name": "foo", "books":["foo", "bar"]}'
curl -s -H "Content-Type: application/json" -w '\n' localhost:8080/data -d '{"foo":"bar"}'
curl -s -H "Content-Type: application/json" -w '\n' localhost:8080/data -d '{"role":"author", "name": "bar", "books":["baz"]}'

curl -s -w '\n' localhost:8080/authors/1
curl -s -w '\n' localhost:8080/authors/2 
```