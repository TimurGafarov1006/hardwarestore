document.addEventListener('DOMContentLoaded', function () {
    const contextPath = document.body.dataset.contextPath;
    const searchInput = document.getElementById('searchInput');
    const suggestionsBox = document.getElementById('searchSuggestions');

    if (!searchInput || !suggestionsBox) return;

    let debounceTimer;

    searchInput.addEventListener('input', function () {
        clearTimeout(debounceTimer);
        const query = this.value.trim();

        if (query.length === 0) {
            suggestionsBox.style.display = 'none';
            return;
        }

        debounceTimer = setTimeout(() => {
            fetch(`${contextPath}/search?query=${encodeURIComponent(query)}`)
                .then(response => {
                    if (!response.ok) throw new Error('Network response was not ok');
                    return response.json();
                })
                .then(products => {
                    suggestionsBox.innerHTML = '';
                    if (!Array.isArray(products) || products.length === 0) {
                        suggestionsBox.style.display = 'none';
                        return;
                    }

                    products.forEach(p => {
                        const div = document.createElement('div');
                        div.textContent = p.name;
                        div.addEventListener('click', () => {
                            window.location.href = `${contextPath}/products/${p.slug}`;
                        });
                        suggestionsBox.appendChild(div);
                    });
                    suggestionsBox.style.display = 'block';
                })
                .catch(err => {
                    console.error('Ошибка поиска:', err);
                    suggestionsBox.style.display = 'none';
                });
        }, 300);
    });

    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !suggestionsBox.contains(e.target)) {
            suggestionsBox.style.display = 'none';
        }
    });
});