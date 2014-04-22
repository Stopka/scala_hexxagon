/**
 * Created by stopka on 22.4.14.
 */
function click(x,y){
    window.document.getElementById("x").value=x;
    window.document.getElementById("y").value=y;
    window.document.getElementById("board_form").submit();
}