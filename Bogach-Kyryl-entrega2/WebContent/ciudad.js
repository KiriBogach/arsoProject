let url_string = window.location.href;
let url = new URL(url_string);
let idGeonames = url.searchParams.get('idGeonames');

if (!idGeonames || idGeonames.length <= 0) {
    throw new Error("Indique el idGeonames");
}

$(function() {
    $.ajax({
        url: REST_URL + 'ciudades/' + idGeonames,
        type: 'get',
        crossDomain: true,
        dataType: 'json',  // esperamos una respuesta en json
        xhrFields: {
            withCredentials: false
        },
        success: function (data) {
            let $population = $('#population');
            $population.html($population.html() + data.poblacion);

            let $location = $('#location');
            $location.html($location.html() + data.ubicacion.latitud + ', ' + data.ubicacion.altitud);

            let $weather = $('#weather');
            $weather.html($weather.html() + data.infoMeteorologica.temperatura + ' ºC, ' + data.infoMeteorologica.nubes);

            let $listaLinks = $('#lista-links');
            $listaLinks.append(
                $('<li>').append(
                    $('<a>').attr('href', data.bdpedia)
                        .attr('class', 'ui-btn ui-btn-icon-right ui-icon-carat-r')
                        .attr('rel', 'external')
                        .append(
                            $('<span>').attr('class', 'tab').append('DBpedia')
                        ))
            );

            $listaLinks.append(
                $('<li>').append(
                    $('<a>').attr('href', data.wikipedia)
                        .attr('class', 'ui-btn ui-btn-icon-right ui-icon-carat-r')
                        .attr('rel', 'external')
                        .append(
                            $('<span>').attr('class', 'tab').append('Wikipedia')
                        ))
            );

            $listaSitios = $('#lista-sitios');
            let sitiosInteres = data.sitiosInteres.sitio;
            for (let indexSitio in sitiosInteres) {
                let sitio = sitiosInteres[indexSitio];
                $listaSitios.append(
                    $('<li>').append(
                        $('<a>')
                            .attr('class', 'ui-btn ui-icon-carat-r')
                            .append(
                                $('<span>').attr('class', 'tab').append(sitio.nombre)
                            ))
                );

            }

            let $listaLibros= $('#lista-libros');
            let libros = data.libros.libro;
            for (let indexLibro in libros) {
                let libro = libros[indexLibro];
                $listaLibros.append(
                    $('<a>')
                        .attr('href', libro.urlGoogleBooks)
                        .attr('class', 'ui-btn ui-icon-carat-r')
                        .attr('rel', 'external')
                        .append(
                            $('<span>').attr('class', 'tab').append(libro.titulo)
                        )
                );

                $listaLibros.append(
                    $('<img>').attr('src', libro.urlImagen).append(libro.urlImagen)
                )

            }
        },
        error: function () {
            alert('Error en el AJAX');
        }
    });

});