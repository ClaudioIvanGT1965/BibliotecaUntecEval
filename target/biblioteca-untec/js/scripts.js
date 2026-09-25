function abrirModalNuevo() {
    document.getElementById('modalTitle').innerText = 'Ingresar Nuevo Libro';
    document.getElementById('libroId').value = '';
    document.getElementById('btnEliminar').style.display = 'none';
    document.getElementById('modalLibro').style.display = 'flex';
}

function abrirModalEditar(id, stock, idArea) {
    document.getElementById('modalTitle').innerText = 'Editar Libro';
    document.getElementById('libroId').value = id;
    document.getElementById('libroStock').value = stock;
    document.getElementById('libroArea').value = idArea;
    document.getElementById('btnEliminar').style.display = 'inline-block';
    document.getElementById('modalLibro').style.display = 'flex';
}

function cerrarModal() {
    document.getElementById('modalLibro').style.display = 'none';
}

function eliminarRegistro() {
    let id = document.getElementById('libroId').value;
    if (confirm("¿Desea eliminar este registro?")) {
        window.location.href = "eliminarLibro?id=" + id;
    }
}