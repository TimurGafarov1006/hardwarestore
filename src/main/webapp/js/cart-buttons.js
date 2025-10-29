document.addEventListener('DOMContentLoaded', () => {
    const contextPath = document.body.dataset.contextPath;

    document.querySelectorAll('.increase').forEach(button => {
        button.addEventListener('click', async function () {
            const item = this.closest('.cart-item');
            const cartId = item.dataset.cartId ? parseInt(item.dataset.cartId, 10) : null;
            const userId = item.dataset.userId;
            const productId = parseInt(item.dataset.productId, 10);
            const currentQty = parseInt(item.querySelector('.quantity').textContent, 10);
            const newQty = currentQty + 1;

            item.querySelector('.quantity').textContent = newQty;

            await updateCart(item, { id: cartId, userId, productId, quantity: newQty }, currentQty);
        });
    });

    document.querySelectorAll('.decrease').forEach(button => {
        button.addEventListener('click', async function () {
            const item = this.closest('.cart-item');
            const cartId = item.dataset.cartId;
            const userId = item.dataset.userId;
            const productId = item.dataset.productId;
            const currentQty = parseInt(item.querySelector('.quantity').textContent, 10);
            const newQty = currentQty - 1;

            if (newQty <= 0) {
                // Если количество <= 0 — удаляем товар
                if (confirm('Удалить товар из корзины?')) {
                    await deleteCartItem(item, cartId);
                } else {
                }
                return;
            }

            item.querySelector('.quantity').textContent = newQty;
            await updateCart(item, { id: cartId, userId, productId, quantity: newQty }, currentQty);
        });
    });

    document.querySelectorAll('.remove').forEach(button => {
        button.addEventListener('click', async function () {
            const item = this.closest('.cart-item');
            const cartId = item.dataset.cartId;
            if (confirm('Удалить товар из корзины?')) {
                await deleteCartItem(item, cartId);
            }
        });
    });


    async function updateCart(item, payload, rollbackQty) {
        try {
            const response = await fetch(contextPath + '/cart', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error('Ошибка сервера');
            }
        } catch (error) {
            item.querySelector('.quantity').textContent = rollbackQty;
            alert('Не удалось обновить корзину');
        }
    }

    async function deleteCartItem(item, cartId) {
        try {
            const response = await fetch(contextPath + '/cart', {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ id: cartId })
            });

            if (response.ok) {
                window.location.reload();
            } else {
                throw new Error('Не удалось удалить');
            }
        } catch (error) {
            alert('Ошибка при удалении товара');
        }
    }
});