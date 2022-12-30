package models

class User(val username: String, val password: String)

object User {
  var users: Set[User] = Set(
    new User("Nate", "miauw"),
    new User("Ruben", "woef")
  )

  def findUser(username: String): Option[User] = {
    val u: Option[User] = users.find(_.username == username)
    u
  }
  def createUser(username: String, password:String): Unit = {

    if(!users.exists(_.username == username)){
      val newUser: User = new User(username, password)
      users += newUser
    }
  }
}