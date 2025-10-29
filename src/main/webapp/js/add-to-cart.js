document.addEventListener('DOMContentLoaded', function() {
    const button = document.getElementById('addToCartBtn');
    if (!button) return; // на случай, если страница без кнопки

    button.addEventListener('click', function() {
        const contextPath = document.body.dataset.contextPath;
        const container = this.closest('.add-to-cart-button');

        const userId = container.dataset.userId;
        const productId = parseInt(container.dataset.productId, 10);

        const data = {
            id: null,
            userId: userId,
            productId: productId,
            quantity: 1,
            redirectAfter: true
        };

        fetch(contextPath + '/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                if (result.redirect) {
                    window.location.href = result.redirect;
                } else if (result.success) {
                    alert('Товар добавлен в корзину!');
                } else {
                    alert('Неизвестная ошибка');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Произошла ошибка сети');
            });
    });
});