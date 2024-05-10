$(document).ready(function() {
    $('#apply-coupon-btn').on('click',function(){
        var amount=$('#amount').text();
        var couponno=$('#coupon-code').text();;
        $.ajax({
            method:'POST',
            url: 'http://localhost:8080/Shoppingcart/DiscountServlet',
            data: {'amount':amount,'coupon':couponno},
             success: function(response) {
                console.log(response);
                var amount=$('#amount').text();
                var i=amount-response;
                $('#amount').text(i);
            },
            error: function(xhr, status, error) {
                console.error('AJAX request failed:', error);
            }
    });
    });
});


