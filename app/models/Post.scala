package models

import java.time.format.DateTimeFormatter

abstract class Post(id: String, user: String, var votes: Int = 0,text: String) {
  val date: String = DateTimeFormatter.ofPattern("dd-MM-YYYY").format(java.time.LocalDate.now)
  def upVote(): Int = {
    votes = votes + 1
    votes
  }
  def downVote(): Int = {
    if (votes > 0) votes -= 1
    votes
  }
}
