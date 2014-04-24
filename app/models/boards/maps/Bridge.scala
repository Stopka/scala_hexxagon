package models.boards.maps

import models.Field

/**
   * Created by stopka on 20.4.14.
   */
class Bridge extends Map{
      def getPlayerCount()=1
      protected def create()=Array.tabulate[Field](9, 9) {
        (x, y) => {
          if (x < 3 || x > 5||(y==4)||(y==3&&x==5)||(y==5&&x==3)||(x==4&&(y==0||y==8))||(x==3&&(y==0||y==1)||(x==5&&(y==7||y==8)))) {
            if((x==0&&y==0)||(x==8&&y==0)||(x==4&&y==8)) {
              new Field(x, y, 0)
            }else {
              if((x==8&&y==8)||(x==0&&y==8)||(x==4&&y==0)) {
                new Field(x, y, 1)
              }else {
                new Field(x, y, -1)
              }
            }
          } else {
              null
          }
        }
      }
  }
