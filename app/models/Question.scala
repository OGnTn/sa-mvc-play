package models

import java.time.format.DateTimeFormatter
import java.util.{Calendar, Date}
import scala.sys

class Question(val id: String, val user: String, votes: Int, val title: String, val text: String, val code: String) extends Post(id, user, votes, text)

object Question {
  var questions: Set[Question] = Set(
    new Question("0", "Nate", 0, "post_0", "I've created a table in supabase and filled it up with some data, Is there anyway to download this table without interacting with supabase client? I want to save this as csv file for future use but there's no apparent feature to extract entire table from supabase, any solution for this?\n", "public class ContractConsumerTests {\n\n    @Autowired\n    private MockMvc mockMvc;\n\n    @Pact(provider = \"provider1\", consumer = \"my_consumer\")\n    public V4Pact provider1Pact(PactBuilder builder) {\n        ...\n    }\n\n    @Pact(provider = \"provider2\", consumer = \"my_consumer\")\n    public V4Pact provider2Pact(PactBuilder builder) {\n        ...\n    }"),
    new Question("1", "Ruben", 5, "post_1", "text1", "code1"),
    new Question("2", "Elish", 10, "post_2", "text2", "code2"),
  )

  def findAll(sortedDate: Boolean): List[Question] = {
    if(sortedDate){
      questions.toList.sortBy(_.date)
    }
    else {
      questions.toList.sortBy(_.votes)(Ordering.Int.reverse)
    }
  }

  def findById(_id: String): Question = questions.find(_.id == _id).getOrElse(new Question("","",0,"","", ""))
  def add(post: Question): Unit =
    questions = questions + post
}