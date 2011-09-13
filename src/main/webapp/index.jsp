<!doctype html>
<html>
<head>
<title>Start</title>
    <meta charset=utf-8" />

    <link rel="stylesheet" type="text/css" href="css/main.css" />   
    <script src="js/jquery-1.6.1.min.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    
    $(document).ready(function() {
        var ws = new WebSocket("ws://localhost:8080" + window.location.pathname + "quotes");
    	
    	$('#disconnect').click(function() {
    		   ws.send('disconnect');	   
    	});
    	
    	ws.onopen = function() {
    		   ws.send("connect");
    	};
    
    	ws.onmessage = function(event) {
    		   var json = eval(event.data);
    		   $('#quotes tbody tr').remove();
    		   
    		   for(var i = 0; i < json.length; i++) {
    			   $('#quotes tbody').append('<tr class="' + (i % 2 == 0 ? 'even' : 'odd') + '">' + renderQuoteRow(json[i]) + '</tr>');
    		   }
    	};
    });
    
    function renderQuoteRow(quote) {
    	var html = renderTd(quote.symbol);
    	html += renderTd(quote.name);
    	html += renderTd(quote.lastTrade);
    	html += renderTd(quote.lastTradeDate + ' ' + quote.lastTradeTime);
    	html += renderTd(quote.highValue);
    	html += renderTd(quote.lowValue);
    	html += renderTd(quote.pricePerEarning);
    	return html;
    }
    
    function renderTd(body) {
    	return '<td>' + body + '</td>';
    }
    
    </script>
    
    <!--[if lt IE 9]>
        <script src="js/html5.js"></script>
    <![endif]-->
    
</head>
<body>
<button id="disconnect">Disconnect</button>
<table id="quotes"> 
<thead> 
<tr> 
    <th>Symbol</th> 
    <th>Name</th> 
    <th>Last Trade</th> 
    <th>Time</th> 
    <th>High</th>
    <th>Low</th>
    <th>P/E</th> 
</tr> 
</thead> 
<tbody> 


</tbody>
</table>
<div id="quotes2">

</div>
</body>
</html>
