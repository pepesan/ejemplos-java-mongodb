function cubo(n) {
    if (typeof n !== "number" || Number.isNaN(n)) {
        throw new TypeError("El parámetro debe ser un number válido");
    }
    return n * n * n;
}