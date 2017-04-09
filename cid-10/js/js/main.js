
function readSingleFile(e) {
    console.log(e.target.files[0]);
    var file = e.target.files[0];
    if (!file) {
        return;
    }

    var reader = new FileReader();
    reader.onload = function(e) {
        var contents = e.target.result;
        displayContents(contents);
        displayTable(contents);

        var id = document.getElementById('cid10-capitulos');
        buildHtmlTable(id, JSON.parse(contents));
    };

    reader.readAsText(file);
}

function displayContents(contents) {
    var element = document.getElementById('file-content');
    element.innerHTML = contents;
}

function displayTable(contents) {
    var dj = JSON.parse(contents);
    alert(dj.NUMCAP[0]);
}

// Builds the HTML Table out of myList.
function addValorCelula(cellValue, row$) {
    if (cellValue == null) cellValue = "";
    row$.append($('<td/>').html(cellValue));
}

function buildHtmlTable(selector, contents) {
    var columns = columnHeadersForCapitulos(selector);

    var numberOfEntries = contents.NUMCAP.length;

    for (var i = 0; i < numberOfEntries; i++) {
        var row$ = $('<tr/>');

        addValorCelula(contents.NUMCAP[i], row$);
        addValorCelula(contents.CATINIC[i], row$);
        addValorCelula(contents.CATFIM[i], row$);
        addValorCelula(contents.DESCRICAO[i], row$);

        $(selector).append(row$);
    }
}

// Monta header da tabela para CAPITULOS
function columnHeadersForCapitulos(selector) {
    var headerTr$ = $('<tr/>');

    headerTr$.append($('<th/>').html("NUMCAP"));
    headerTr$.append($('<th/>').html("CATINIC"));
    headerTr$.append($('<th/>').html("CATFIM"));
    headerTr$.append($('<th/>').html("DESCRICAO"));

    $(selector).append(headerTr$);
}

document.getElementById('file-input')
    .addEventListener('change', readSingleFile, false);
