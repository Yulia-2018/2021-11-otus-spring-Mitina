### curl samples
> For windows use `Git Bash`

### Books
#### get All Books
`curl -s http://localhost:8080/books`

#### get Book
`curl -s http://localhost:8080/books/Book_1`

#### delete Book
`curl -s -X DELETE http://localhost:8080/books/Book_2`

#### create Book
`curl -s -X POST -d '{"title":"New book","author":{"name":"author 2"},"genre":{"title":"New Genre"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/books`

#### update Book
`curl -s -X PUT -d '{"title":"Updated book","author":{"name":"New author"},"genre":{"title":"genre 2"}}' -H 'Content-Type: application/json' http://localhost:8080/books/Book_1`

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
`curl -s -X PUT -d '{"text":"Updated comment","book":{"id":100005}}' -H 'Content-Type: application/json' http://localhost:8080/comments/100007`