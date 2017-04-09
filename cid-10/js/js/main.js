
// Realiza operação com o conteúdo fornecido
// Nesse caso, supõe que conteúdo de capítulos
// é o contúdo fornecido.

function doSomeWork(contents) {
    var id = document.getElementById('cid10-capitulos');
    adicionaLinhasEmTabelaDeCapitulos(id, JSON.parse(contents));
}

// Exibe conteúdo "bruto"
function displayContents(contents) {
    var element = document.getElementById('file-content');
    element.innerHTML = contents;
}

function displayTable(contents) {
    var dj = JSON.parse(contents);
    alert(dj.NUMCAP[0]);
}

// Acrescenta coluna (valor) em linha de tabela
function adicionaColunaEmLinha(cellValue, row$) {
    if (cellValue == null) cellValue = "";
    row$.append($('<td/>').html(cellValue));
}

// Adiciona linhas na tabela de capítulos
function adicionaLinhasEmTabelaDeCapitulos(selector, contents) {

    cabecalho(selector, contents);

    var numberOfEntries = contents.NUMCAP.length;

    for (var i = 0; i < numberOfEntries; i++) {
        var row$ = $('<tr/>');

        adicionaColunaEmLinha(contents.NUMCAP[i], row$);
        adicionaColunaEmLinha(contents.CATINIC[i], row$);
        adicionaColunaEmLinha(contents.CATFIM[i], row$);
        adicionaColunaEmLinha(contents.DESCRICAO[i], row$);

        $(selector).append(row$);
    }
}

// Monta cabeçalho com propriedades do objeto
function cabecalho(selector, objetoJson) {
    var headerTr$ = $('<tr/>');

    for (var key in objetoJson) {
        headerTr$.append($('<th/>').html(key));
    }

    $(selector).append(headerTr$);
}

// ---------------------------
// Carrega o arquivo
// ---------------------------
function readSingleFile(e) {
    var file = e.target.files[0];
    if (!file) {
        return;
    }

    var reader = new FileReader();

    reader.onload = function(e) {
        var contents = e.target.result;
        doSomeWork(contents);
    };

    reader.readAsText(file);
}

// ----------------------------------
// Registra evento com readSingleFile
// ----------------------------------

document.getElementById('file-input')
    .addEventListener('change', readSingleFile, false);
