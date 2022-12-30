package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, ControllerComponents, Flash}
import models.Question
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.User
import play.api.i18n.I18nSupport

case class UserForm(username: String, password: String)
class Users @Inject() (cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  private val userForm: Form[UserForm] =
    Form(mapping("username" -> nonEmptyText.verifying("User already exists.", User.findUser(_).isEmpty),
                 "password" -> nonEmptyText)
      (UserForm.apply)(UserForm.unapply))
  def newUser() = Action { implicit request =>
    val form = if (request.flash.get("error").isDefined)
      userForm.bind(request.flash.data)
    else
      userForm
      Ok(views.html.createUser(form))
  }

  def loginForm() = Action { implicit request =>
    val form = if(request.flash.get("error").isDefined)
      userForm.bind(request.flash.data)
    else
      userForm
    Ok(views.html.loginUser(form))
  }

  def login() = Action { implicit request =>
    val newForm = userForm.bindFromRequest()

    newForm.fold(
      hasErrors = { form =>
        Redirect(routes.Users.loginForm()).flashing(Flash(form.data) + ("Error" -> "Fix neef"))
      },

      success = { loginUser =>
        val u: User = User.findUser(loginUser.username).getOrElse(Redirect(routes.Posts.listByDate()))
        if(loginUser.password == u.password){
          //user succesfully logged in
          Redirect(routes.Posts.listByDate()).withSession("loggedIn" -> loginUser.username)
        }
        else {
          //Wrong password
          Redirect(routes.Users.loginForm()).flashing(Flash(newForm.data) + ("Error" -> "Wrong Password"))
        }
      }
    )
  }

  def createUser() = Action { implicit request =>
    val newForm = userForm.bindFromRequest()

    newForm.fold(
      hasErrors = { form =>
        Redirect(routes.Users.newUser()).flashing(Flash(form.data) +
          ("error" -> "Fix neef"))
      },

      success = { newUser =>
        User.createUser(newUser.username, newUser.password)
        val msg = "User succesfully created " + newUser.username
        Redirect(routes.Posts.listByDate()).flashing("success" -> msg)
      }
    )
  }
}
