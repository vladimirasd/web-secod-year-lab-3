function drawBasics(){

    const canvas = document.getElementById("area");
    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;

    // Чистим
    ctx.clearRect(0, 0, width, height);

    //Задаем фон
    ctx.fillStyle = '#ffffff';
    ctx.fillRect(0, 0, width, height);

}

function drawCoordinateSystem() {

    const canvas = document.getElementById("area");
    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;

    // Центр координат
    const centerX = width / 2;
    const centerY = height / 2;

    // Шаг сетки
    const gridSize = 50;

    // Параметры для сеточки
    ctx.strokeStyle = '#333';
    ctx.lineWidth = 0.5;

    for(let x = gridSize; x < width; x += gridSize){

        ctx.beginPath();
        ctx.moveTo(x, 0);
        ctx.lineTo(x, height);
        ctx.stroke();

    }

    for(let y = gridSize; y < height; y += gridSize){

        ctx.beginPath();
        ctx.moveTo(0, y);
        ctx.lineTo(width, y);
        ctx.stroke();

    }

    // Параметры для осей
    ctx.strokeStyle = '#000';
    ctx.lineWidth = 1;

    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.stroke();


    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.stroke();

    // Подписи осей
    ctx.font = '20px Arial';
    ctx.fillStyle = '#000';
    ctx.fillText('X', width - 20, centerY - 10);
    ctx.fillText('Y', centerX + 10, 20);

    // Разметочка для осей
    ctx.font = '15px Arial';

    for( y = gridSize; y < height; y += gridSize){
        if( y !== centerY){

            const value = -( y - centerY)/gridSize;
            ctx.fillText(value, centerX + 10, y);

            ctx.beginPath();
            ctx.moveTo(centerX - 5, y);
            ctx.lineTo(centerX + 5, y);
            ctx.stroke();

        }
    }

    for( x = gridSize; x < width; x += gridSize){
        if( x !== centerX){

            const value = ( x - centerX)/gridSize;
            ctx.fillText(value, x, centerY - 10);

            ctx.beginPath();
            ctx.moveTo(x, centerX - 5);
            ctx.lineTo(x, centerY + 5);
            ctx.stroke();

        }
    }

    ctx.fillText(0, centerX + 10, centerY - 10)

}

function drawPoints(){

    const globalR = document.getElementById("data-form:hiddenR").value;

    const canvas = document.getElementById("area");
    const rect = canvas.getBoundingClientRect();
    const ctx = canvas.getContext("2d");

    const scaleX = canvas.width / rect.width;
    const scaleY = canvas.height / rect.height;
    const centerX = canvas.width/2;
    const centerY = canvas.height/2;

    var rawPoints= document.getElementById("canvasForm:allPoints").value;
    var points = JSON.parse(rawPoints);

    points.forEach( point => {

        const posX = parseFloat(point.x);
        const posY = parseFloat(point.y);
        const r = parseFloat(point.r);

        if(point.isHit == true && r <= globalR){

            ctx.fillStyle = '#0ff';
            ctx.beginPath();
            ctx.arc(posX*50 + centerX, -posY*50 + centerY, 3, 0, Math.PI * 2);
            ctx.fill();

        }
        if(point.isHit == false && r >= globalR){

            ctx.fillStyle = '#f00';
            ctx.beginPath();
            ctx.arc(posX*50 + centerX, -posY*50 + centerY, 3, 0, Math.PI * 2);
            ctx.fill();

        }



    }


    );

}

function drawArea(){

    const r = document.getElementById("data-form:hiddenR").value;
    console.log(r);
    const canvas = document.getElementById("area");
    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;

    ctx.fillStyle = '#3000EE';

    // Центр координат
    const centerX = width / 2;
    const centerY = height / 2;


    //4 четверть
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX - 50 * r, centerY);
    ctx.lineTo(centerX, centerY - 50 * r);
    ctx.closePath();
    ctx.fill();

    //2 четверть
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + 25 * r, centerY);
    ctx.lineTo(centerX, centerY - 25 * r);
    ctx.closePath();
    ctx.fill();
    ctx.beginPath();
    ctx.arc(centerX, centerY, 25*r, 3*Math.PI/2, 2*Math.PI);
    ctx.closePath();
    ctx.fill();

    //1 четверть
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + 25 * r, centerY);
    ctx.lineTo(centerX + 25 * r, centerY + 50 * r);
    ctx.lineTo(centerX, centerY + 50 * r);
    ctx.closePath();
    ctx.fill();


}


function drawAll(){
    drawBasics();
    drawArea();
    drawCoordinateSystem();
    drawPoints();
}


document.addEventListener('DOMContentLoaded', function() {

    restoreSession();

    const targetElement = document.getElementById('data-form:hiddenR');

    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            if (mutation.type === 'attributes' && mutation.attributeName === 'value') {
                drawAll();
            }
        });
    });

    observer.observe(targetElement, {
        attributes: true,
        attributeFilter: ['value']
    });

    const beanObserver = new MutationObserver(function(mutations){
            mutations.forEach(function(mutation) {

                const target = mutation.target;

                if (mutation.type === 'attributes' && mutation.attributeName === 'value') {
                        const id = target.id;
                        const newValue = target.value;
                        localStorage.setItem(id, newValue);
                }

            });
    });

    const targetX = document.getElementById('data-form:xInput');
    const targetY = document.getElementById('data-form:yInputHidden');
    const targetR = document.getElementById('data-form:decimalSlider_hidden');

    beanObserver.observe(targetX, {
        attributes: true,
        attributeFilter: ['value']
    });
    beanObserver.observe(targetY, {
        attributes: true,
        attributeFilter: ['value']
    });
    beanObserver.observe(targetR, {
        attributes: true,
        attributeFilter: ['value']
    });

    drawAll();
    document.getElementById("area").addEventListener("click", function(e){

        const canvas = document.getElementById("area");
        const rect = canvas.getBoundingClientRect();

        const scaleX = canvas.width / rect.width;
        const scaleY = canvas.height / rect.height;
        const centerX = canvas.width/2;
        const centerY = canvas.height/2;

        const posX = ((e.clientX - rect.left)*scaleX - centerX)/50;
        const posY = -((e.clientY - rect.top)*scaleY - centerY)/50;

        document.getElementById("canvasForm:xAreaInput").value = posX;
        document.getElementById("canvasForm:yAreaInput").value = posY;
        document.getElementById("canvasForm:rAreaInput").value = document.getElementById("data-form:hiddenR").value;;
        document.getElementById('canvasForm:canvasSubmit').click();
        setTimeout(drawAll, 400);
    });
});


function restoreSession(){

    const prefX = localStorage.getItem("data-form:xInput");
    const prefY = localStorage.getItem("data-form:yInputHidden")
    const prefR =  localStorage.getItem("data-form:decimalSlider_hidden");

    const curX = document.getElementById('data-form:xInput').value;
    const curY = document.getElementById('data-form:yInputHidden').value;
    const curR = document.getElementById('data-form:rInput').value;

    if(prefX && !curX){
        document.getElementById('data-form:xInput').value = prefX;
        jsf.ajax.request('data-form:restoreSubmit', null, {
            execute: 'data-form:xInput',
            render: 'data-form:legendX data-form:xInput data-form:xRadio',
        });
    }
    if(prefY && !curY){
        document.getElementById('data-form:yInputHidden').value = prefY;
        jsf.ajax.request('data-form:restoreSubmit', null, {
            execute: 'data-form:yInputHidden',
            render: 'data-form:legendY data-form:yInput',
        });
    }

    if(prefR && curR == 0){
       document.getElementById('data-form:rInput').value = parseFloat(prefR);
       jsf.ajax.request('data-form:restoreSubmit', null, {
           execute: 'data-form:rInput',
           render: 'data-form:decimalSlider data-form:legendR data-form:hiddenR',
       });
    }

}



