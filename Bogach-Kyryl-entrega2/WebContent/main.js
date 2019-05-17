$(function() {

    $('#boton-busqueda').click(function(){
        let busqueda = $('#input-busqueda').val();

        if (!busqueda || busqueda.length <= 0) {
            alert('Inserte un valor válido en el formulario');
            return;
        }

        $.ajax({
            url: REST_URL + 'ciudades?hal&pagina=1&busqueda=' + busqueda,
            type: 'get',
            crossDomain: true,
            dataType: 'json',  // esperamos una respuesta en json
            xhrFields: {
                withCredentials: false
            },
            success: function (data) {
                let $resultadoBusqueda = $('#resultado-busqueda');
                if (data.count <= 0) {
                    alert('No se han encontrado ciudades para la entrada introducida');
                    $resultadoBusqueda.hide();
                    return;
                }
                $resultadoBusqueda.show();

                let $lista = $('#lista-busqueda');
                let ciudades = data._embedded.ciudades;
                $lista.empty();

                for (let indexCiudad in ciudades) {
                    let ciudad = ciudades[indexCiudad];
                    let nombreCiudad = ciudad.nombre + ' (' + ciudad.pais + ')';
                    let idGeoNames = ciudad.id;

                    $lista.append(
                        $('<li>').append(
                            $('<a>').attr('href', URL_DEPLOYMENT + 'ciudad.html?idGeonames=' + idGeoNames)
                                    .attr('class', 'ui-btn ui-btn-icon-right ui-icon-carat-r')
                                    .attr('rel', 'external')
                                    .append(
                                $('<span>').attr('class', 'tab').append(nombreCiudad)
                            )));

                }

                console.log(data);
            },
            error: function () {
                alert('Error en el AJAX');
            }
        });
    });
});