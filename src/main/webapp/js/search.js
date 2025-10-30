document.addEventListener('DOMContentLoaded', function () {
    const contextPath = document.body.dataset.contextPath;
    const searchInput = document.getElementById('searchInput');
    const suggestionsBox = document.getElementById('searchSuggestions');
    const template = document.getElementById('searchSuggestionTemplate');

    if (!searchInput || !suggestionsBox || !template) return;

    let debounceTimer;

    function hideSuggestions() {
        suggestionsBox.innerHTML = '';
        suggestionsBox.style.display = 'none';
    }

    function createSuggestionItem(product) {
        const clone = document.importNode(template.content, true);

        const img = clone.querySelector('.suggestion-image');
        const nameEl = clone.querySelector('.suggestion-name');
        const priceEl = clone.querySelector('.suggestion-price');

        nameEl.textContent = product.name;
        priceEl.textContent = `${product.pricePerUnit} ₽`;

        const imageUrl = product.imageUrl
            ? (product.imageUrl.startsWith('/') ? product.imageUrl : '/' + product.imageUrl)
            : '/images/placeholder.png';

        img.src = contextPath + imageUrl;
        img.alt = product.name;

        const item = clone.querySelector('.search-suggestion-item');
        item.addEventListener('click', () => {
            window.location.href = `${contextPath}/products/${product.slug}`;
        });

        return clone;
    }

    async function fetchAndRender(query) {
        try {
            const response = await fetch(`${contextPath}/search?query=${encodeURIComponent(query)}`);
            if (!response.ok) throw new Error('Network error');
            const products = await response.json();

            hideSuggestions();
            if (!Array.isArray(products) || products.length === 0) return;

            products.forEach(product => {
                suggestionsBox.appendChild(createSuggestionItem(product));
            });
            suggestionsBox.style.display = 'block';
        } catch (err) {
            console.error('Ошибка поиска:', err);
            hideSuggestions();
        }
    }

    searchInput.addEventListener('input', function () {
        clearTimeout(debounceTimer);
        const query = this.value.trim();
        if (query.length === 0) {
            hideSuggestions();
            return;
        }
        debounceTimer = setTimeout(() => fetchAndRender(query), 300);
    });

    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !suggestionsBox.contains(e.target)) {
            hideSuggestions();
        }
    });
});