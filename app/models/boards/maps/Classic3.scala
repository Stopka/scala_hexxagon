package models.boards.maps

import models.Field

/**
  * Created by stopka on 20.4.14.
  */
class Classic3 extends Map{
     def getPlayerCount()=3
     protected def create()=Array.tabulate[Field](9,9){
       (x,y)=>{
         if(x+y<=3||x+y>=13||(x==4&&y==3)||(x==3&&y==5)||(x==5&&y==4)){
           null
         }else{
           if((x==0&&y==4)||(x==8&&y==4)) {
             new Field(x,y,0)
           }else{
             if((x==0&&y==8)||(x==8&&y==0)) {
               new Field(x,y,1)
             }else{
               if((x==4&&y==8)||(x==4&&y==0)) {
                 new Field(x,y,2)
               }else{
                 new Field(x,y,-1)
               }
             }
           }
         }
       }
     }
 }
