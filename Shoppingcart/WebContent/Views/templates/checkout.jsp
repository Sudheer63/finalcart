<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.BillingDetails" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout</title>
    <link rel="stylesheet" href="../css/checkout.css">
    <!-- Latest compiled and minified CSS -->
    <!-- Bootstrap CDN link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../javascript/checkout.js"></script>
</head>
<body>
    <table class="table-class">
        <thead>
            <tr>
                <th>SNo</th>
                <th>Item Name</th>
                <th>Item Price</th>
                <th>Item Quantity</th>
                <th>Price(no gst)</th>
                <th>in gst</th>
                <th>Total Cost</th>
                <th>Shipping charges</th>
                <th>gst(sc)</th>
                <th>Final Price</th>
            </tr>
        </thead>
        <tbody>
            <% 
            HttpSession sessionObj = request.getSession(false);
            List<BillingDetails> bill = (List<BillingDetails>) sessionObj.getAttribute("bill");
            if (bill != null) {
                int sno = 1;
                for (BillingDetails detail : bill) {
            %>
            <tr>
                <td><%= sno++ %></td>
                <td><%= detail.getProdname() %></td>
                <td><%= detail.getPrice()  %></td>
                <td><%= detail.getQuantity()%></td>
                <td><%= detail.getPricewithoutgst() %></td>
                <td><%= detail.getIngst() %></td>
                <td><%= detail.getTotalpriceperquantity() %></td>
                <td><%= detail.getSc() %></td>
                <td><%= detail.getScgst() %></td>
                <td><%= detail.getFinalprice() %></td>
            </tr>
            <% 
                }
            }
            %>
        </tbody>
    </table>
    <div>
    	<h1>Total Amount : <span id="amount"> </span></h1>
    </div>
    <div class="coupon-container">
        <h2>Apply Coupon</h2>
        <div class="coupon-form">
            <input type="text" id="coupon-code" placeholder="Enter your coupon code">
            <button id="apply-coupon-btn">Apply</button>
        </div>
    </div>
    <button id="rzp-button1">Pay</button>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script>
 
var tableBody = document.querySelector(".table-class tbody");

//Function to get values of last column in each row
function getLastColumnValues() {
 		 var lastColumnValues = [];
		 var total=0;

		 for (var i = 0; i < tableBody.rows.length; i++) {
		
			 var lastCell = tableBody.rows[i].cells[tableBody.rows[i].cells.length - 1];
			 total += parseFloat(lastCell.textContent);
		     
		 }
		 return total;
		}

document.querySelector("#amount").textContent = getLastColumnValues();

// Example usage:
var lastColumnValues = getLastColumnValues();
console.log("Values of last column:", lastColumnValues);



var options = {
    "key": "rzp_test_igiSdOA5LIjesg", // Enter the Key ID generated from the Dashboard
    "amount": "1000000", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    "currency": "INR",
    "name": "Acme Corp", //your business name
    "description": "Test Transaction",
    "image": "https://example.com/your_logo",
    "order_id": "order_O7yFg6juTs3MOZ", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    "callback_url": "https://eneqd3r9zrjok.x.pipedream.net/",
    "prefill": { //We recommend using the prefill parameter to auto-fill customer's contact information especially their phone number
        "name": "Gaurav Kumar", //your customer's name
        "email": "gaurav.kumar@example.com",
        "contact": "9000090000" //Provide the customer's phone number for better conversion rates 
    },
    "notes": {
        "address": "Razorpay Corporate Office"
    },
    "theme": {
        "color": "#3399cc"
    }
};
var rzp1 = new Razorpay(options);
document.getElementById('rzp-button1').onclick = function(e){
    rzp1.open();
    e.preventDefault();
}
</script>
</body>
</html>
