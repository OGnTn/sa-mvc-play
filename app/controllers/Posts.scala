package controllers



import javax.inject.Inject
import play.api.mvc.{Flash, Action, AbstractController,
  ControllerComponents}
import models.Question
import play.api._
import play.api.mvc._

class Posts @Inject() (cc: ControllerComponents)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def listByDate() = list(true)
  def listByVotes() = list(false)
  def list(sortedDate: Boolean) = Action { implicit request =>
    val posts = Question.findAll(sortedDate)
    Ok(views.html.posts(posts, sortedDate))
  }

  def details(id: String) = Action { implicit request =>

    val post = Question.findById(id)
    Ok(views.html.post(post))
  }

  def upvote(id: String) = Action { implicit request =>
    val post = Question.findById(id)
    Ok(post.upVote().toString)
  }
}
