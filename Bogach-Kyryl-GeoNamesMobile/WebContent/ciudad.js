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

            let $lista = $('#lista-links');
            $lista.append(
                $('<li>').append(
                    $('<a>').attr('href', data.bdpedia)
                        .attr('class', 'ui-btn ui-btn-icon-right ui-icon-carat-r')
                        .attr('rel', 'external')
                        .append(
                            $('<span>').attr('class', 'tab').append('DBpedia')
                        ))
            );

            $lista.append(
                $('<li>').append(
                    $('<a>').attr('href', data.wikipedia)
                        .attr('class', 'ui-btn ui-btn-icon-right ui-icon-carat-r')
                        .attr('rel', 'external')
                        .append(
                            $('<span>').attr('class', 'tab').append('Wikipedia')
                        ))
            );

            $lista = $('#lista-sitios');

            let sitiosInteres = data.sitiosInteres.sitio;

            for (let indexSitio in sitiosInteres) {
                let sitio = sitiosInteres[indexSitio];
                $lista.append(
                    $('<li>').append(
                        $('<a>')
                            .attr('class', 'ui-btn ui-icon-carat-r')
                            .append(
                                $('<span>').attr('class', 'tab').append(sitio.nombre)
                            ))
                );

            }
        },
        error: function () {
            alert('Error en el AJAX');
        }
    });

});