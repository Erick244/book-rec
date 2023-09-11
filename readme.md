## AuthController:

**POST /signup**

* Description: Sign up a new user.
* Request Body: SignUpDto
* Response: ResponseEntity<?>

**POST /login**

* Description: Log in an existing user.
* Request Body: SignInDto
* Response: ResponseEntity<SignInResponseDto>

## BooksController:

**POST /books**  *[ADMIN]*

* Description: Create a new book.
* Request Body: CreateBookDto
* Response: ResponseEntity<?>

**GET /books/findOne/{bookId}**

* Description: Retrieve details of a specific book by bookId.
* Path Variable: bookId (Long)
* Response: ResponseEntity<Book>

**POST /books/addInterests**  *[ADMIN]*

* Description: Add interests for a book.
* Request Body: AddBookInterestsDto
* Response: ResponseEntity<?>

**GET /books/userRecommendations**

* Description: Get user book recommendations with optional pagination.
* Query Parameters:
  * page (default: 0) - Page number
  * take (default: 5) - Number of items to take per page
* Response: ResponseEntity<Iterable<Book>>

## InterestsController:

**GET /interests**

* Description: Retrieve all interests.
* Response: ResponseEntity<Iterable<Interest>>

**POST /interests**  *[ADMIN]*

* Description: Create a new interest.
* Request Body: CreateInterestDto
* Response: ResponseEntity<?>

## UsersController:

**POST /users/setInterests**

* Description: Set user interests.
* Request Body: SetUserInterestsDto
* Response: ResponseEntity<?>

**POST /users/readBook/{bookId}**

* Description: Mark a book as read for the user.
* Path Variable: bookId (Long)
* Response: ResponseEntity<?>

**GET /users/booksRead**

Description: Get a list of books read by the user.
Response: ResponseEntity<List<Book>>
