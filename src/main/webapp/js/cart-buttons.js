document.addEventListener('DOMContentLoaded', () => {
    const contextPath = document.body.dataset.contextPath;

    document.querySelectorAll('.increase').forEach(button => {
        button.addEventListener('click', async function () {
            const item = this.closest('.cart-item');
            if (!item) return;

            const cartId = item.dataset.cartId ? parseInt(item.dataset.cartId, 10) : null;
            const userId = item.dataset.userId;
            const productId = parseInt(item.dataset.productId, 10);
            const qtyElement = item.querySelector('.quantity-display');
            const currentQty = parseInt(qtyElement?.textContent || '1', 10);
            const newQty = currentQty + 1;

            qtyElement.textContent = newQty;

            const success = await updateCart({ id: cartId, userId, productId, quantity: newQty });
            if (!success) {
                qtyElement.textContent = currentQty;
                alert('Не удалось обновить количество');
            }
        });
    });

    document.querySelectorAll('.decrease').forEach(button => {
        button.addEventListener('click', async function () {
            const item = this.closest('.cart-item');
            if (!item) return;

            const cartId = item.dataset.cartId ? parseInt(item.dataset.cartId, 10) : null;
            const userId = item.dataset.userId;
            const productId = parseInt(item.dataset.productId, 10);
            const qtyElement = item.querySelector('.quantity-display');
            const currentQty = parseInt(qtyElement?.textContent || '1', 10);
            const newQty = currentQty - 1;

            if (newQty <= 0) {
                if (confirm('Удалить товар из корзины?')) {
                    await removeCartItem(cartId, item);
                }
                return;
            }

            qtyElement.textContent = newQty;
            const success = await updateCart({ id: cartId, userId, productId, quantity: newQty });
            if (!success) {
                qtyElement.textContent = currentQty;
                alert('Не удалось обновить количество');
            }
        });
    });

    document.querySelectorAll('.remove-btn').forEach(button => {
        button.addEventListener('click', async function () {
            const item = this.closest('.cart-item');
            const cartId = item?.dataset.cartId ? parseInt(item.dataset.cartId, 10) : null;
            if (cartId && confirm('Удалить товар из корзины?')) {
                await removeCartItem(cartId, item);
            }
        });
    });

    async function updateCart(payload) {
        try {
            const response = await fetch(contextPath + '/cart', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json; charset=UTF-8' },
                body: JSON.stringify(payload)
            });
            return response.ok;
        } catch (error) {
            console.error('Ошибка обновления корзины:', error);
            return false;
        }
    }

    async function removeCartItem(cartId, item) {
        try {
            const response = await fetch(contextPath + '/cart', {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json; charset=UTF-8' },
                body: JSON.stringify({ id: cartId })
            });

            if (response.ok) {
                item?.remove();

                const remainingItems = document.querySelectorAll('.cart-item');
                if (remainingItems.length === 0) {
                    window.location.reload();
                }
            } else {
                alert('Не удалось удалить товар');
            }
        } catch (error) {
            console.error('Ошибка удаления:', error);
            alert('Произошла ошибка сети при удалении');
        }
    }
});