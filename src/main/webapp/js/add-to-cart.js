document.addEventListener('DOMContentLoaded', function() {
    const button = document.getElementById('addToCartBtn');
    if (!button) return; // на случай, если страница без кнопки

    button.addEventListener('click', function() {
        const contextPath = document.body.dataset.contextPath;
        const container = this.closest('.add-to-cart-button'); // ← находим контейнер

        const userId = container.dataset.userId;
        const productId = parseInt(container.dataset.productId, 10);

        const data = {
            id: null,
            userId: userId,
            productId: productId,
            quantity: 1
        };

        fetch(contextPath + '/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    alert('Товар добавлен в корзину!');
                } else {
                    alert('Ошибка при добавлении в корзину');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Произошла ошибка сети');
            });
    });
});