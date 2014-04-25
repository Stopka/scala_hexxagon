package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def index = Action {
    request =>
      request.session.get("user").map { user =>
        System.out.println("Index "+user);
        //val id=models.Games.add(user)
        Ok(views.html.index())
        //Redirect(routes.Application.invite(id)).withSession("user"->user)
      }.getOrElse {
        val user=models.Users.add()
        Redirect(routes.Application.index).withSession("user"->user)
      }
  }

  def help = Action {
    request =>
      Ok(views.html.help())
  }

  def start(boardId:String) = Action {
    request =>
      request.session.get("user").map { user =>
        try {
          System.out.println("Start "+boardId+" "+user);
          val id=models.Games.add(user,Integer.parseInt(boardId))
          Redirect(routes.Application.invite(id))
        }catch{
          case e:NoSuchElementException=>Redirect(routes.Application.index())
        }
      }.getOrElse {
        Redirect(routes.Application.index)
      }
  }

  def invite(gameId:String) = Action {
    request =>
      request.session.get("user").map { user =>
        try {
          System.out.println("Board "+gameId+" "+user);
          val game = models.Games(gameId)
          game.addUser(user)

          if (game.arePlayersReady()) {
            Redirect(routes.Application.board(gameId))
          } else {
            Ok(views.html.invite(game, user))
          }
        }catch{
          case e:NoSuchElementException=>Redirect(routes.Application.index())
        }
      }.getOrElse {
        val user=models.Users.add()
        Redirect(routes.Application.invite(gameId)).withSession("user"->user)
      }
  }

  def addLocalPlayer(gameId:String) = Action {
    request =>
      request.session.get("user").map { user =>
        try {
          System.out.println("addLocalPlayer "+gameId+" "+user);
          val game = models.Games(gameId)
          game.addUser(user,false)
          Redirect(routes.Application.invite(gameId))
        }catch{
          case e:NoSuchElementException=>Redirect(routes.Application.index())
        }
      }.getOrElse {
        Redirect(routes.Application.index())
      }
  }

  def addAIPlayer(gameId:String) = Action {
    request =>
      request.session.get("user").map { user =>
        try {
          System.out.println("addAIPlayer "+gameId+" "+user);
          val game = models.Games(gameId)
          game.addUser("AI",false)
          Redirect(routes.Application.invite(gameId))
        }catch{
          case e:NoSuchElementException=>Redirect(routes.Application.index())
        }
      }.getOrElse {
        Redirect(routes.Application.index())
      }
  }

  def board(gameId:String) = Action {
    request =>
      request.session.get("user").map { user =>
        try {
          System.out.println("Board "+gameId+" "+user);
          val game = models.Games(gameId)
          Ok(views.html.board(game,game.getPlayerNumber(user)))
        }catch{
          case e:NoSuchElementException=>Redirect(routes.Application.index())
        }
      }.getOrElse {
        Redirect(routes.Application.index())
      }
  }

  val coordinates = Form(
    tuple(
      "x" -> number,
      "y" -> number
    )
  )

  def click(gameId:String) = Action {
    implicit request =>
      request.session.get("user").map {
        user =>
          try {
            val game = models.Games(gameId)
            val (x, y)=coordinates.bindFromRequest.get
            System.out.println("Click "+gameId+" "+x+","+y+" "+user);
            game.click(user,x,y)
            Redirect(routes.Application.board(gameId))
          }catch{
            case e:NoSuchElementException=>Redirect(routes.Application.index())
          }
      }.getOrElse {
        Redirect(routes.Application.index())
      }
  }
}

