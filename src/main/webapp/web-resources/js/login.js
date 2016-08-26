$('input').mouseup(findTotal);
$('input').keyup(findTotal);


var totalCount = 20;

function findTotal(){
    var arr = document.getElementsByName('qty');
    var tot=0;
    for(var i=0;i<arr.length;i++){
        if(parseInt(arr[i].value))
            tot += parseInt(arr[i].value);
    }
    $('#balls').text(totalCount-tot);
}