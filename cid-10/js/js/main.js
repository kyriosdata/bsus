
// Realiza operação com o conteúdo fornecido
// Nesse caso, supõe que conteúdo de capítulos
// é o contúdo fornecido.

function doSomeWork(contents) {
    var id = document.getElementById('cid10-capitulos');
    exibeTabela(id, JSON.parse(contents));
}

// Exibe conteúdo "bruto"
function displayContents(contents) {
    var element = document.getElementById('file-content');
    element.innerHTML = contents;
}

// Acrescenta coluna (valor) em linha de tabela
function adicionaColunaEmLinha(cellValue, row$) {
    if (cellValue == null) cellValue = "";
    row$.append($('<td/>').html(cellValue));
}

// Adiciona linhas à tabela conforme o contúdo fornecido
function exibeTabela(tabela, objetoJson) {

    cabecalho(tabela, objetoJson);

    var primeiraPropriedade = Object.keys(objetoJson)[0];
    var numberOfEntries = objetoJson[primeiraPropriedade].length;

    for (var i = 0; i < numberOfEntries; i++) {
        var row$ = $('<tr/>');

        for (var key in objetoJson) {
            adicionaColunaEmLinha(objetoJson[key][i], row$);
        }

        $(tabela).append(row$);
    }
}

// Monta cabeçalho com os nomes das propriedades do objeto
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
