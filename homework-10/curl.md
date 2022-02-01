### curl samples
> For windows use `Git Bash`

### Books
#### get All Books
`curl -s http://localhost:8080/books`

#### get Book
`curl -s http://localhost:8080/books/100004`

#### delete Book
`curl -s -X DELETE http://localhost:8080/books/100005`

#### create Book
`curl -s -X POST -d '{"title":"New book","author":{"id":100000},"genre":{"id":100003}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/books`

#### update Book
`curl -s -X PUT -d '{"id":100004,"title":"Updated book","author":{"id":100001},"genre":{"id":100003}}' -H 'Content-Type: application/json' http://localhost:8080/books/100004`

### Comments
#### get All Comments for Book
`curl -s http://localhost:8080/comments?bookId=100004`

#### get Comment
`curl -s http://localhost:8080/comments/100006`

#### delete Comment
`curl -s -X DELETE http://localhost:8080/comments/100006`

#### create Comment
`curl -s -X POST -d '{"text":"New comment","book":{"id":100004}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/comments`

#### update Comment
`curl -s -X PUT -d '{"id":100007,"text":"Updated comment","book":{"id":100004}}' -H 'Content-Type: application/json' http://localhost:8080/comments/100007`