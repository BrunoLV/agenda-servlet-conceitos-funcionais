var moduloManipulacao = (function() {

	const parametros = new URLSearchParams(window.location.search);

	const command = parametros.get('command');

	var telefones = [];
	var indiceTelefone = 0;

	var edicao = command == 'editarContato';

	if (edicao) {

		var tabela = document.getElementById('tabelaTelefones');
		if (tabela != null) {
			var corpoTabela = tabela.tBodies[0];
			for (var i = 0; i < corpoTabela.rows.length; i++) {
				var telefoneAux = {};
				telefoneAux['id'] = corpoTabela.rows[i].children[0].value;
				telefoneAux['ddd'] = corpoTabela.rows[i].cells[0].innerHTML;
				telefoneAux['numero'] = corpoTabela.rows[i].cells[1].innerHTML;
				telefoneAux['tipo'] = corpoTabela.rows[i].cells[2].innerHTML;
				telefones[indiceTelefone++] = telefoneAux;
			}
		}

		document.getElementById('idTelefone').value = null;
		document.getElementById('ddd').value = null;
		document.getElementById('telefone').value = null;
		document.getElementById('tipo').selectedIndex = 0;

	}

	function adiciona() {
		var tabelaTelefones = document.getElementById('tabelaTelefones');
		var telefone = {};

		if (edicao) {

			telefone['id'] = document.getElementById('idTelefone').value;
			telefone['ddd'] = document.getElementById('ddd').value;
			telefone['numero'] = document.getElementById('telefone').value;
			telefone['tipo'] = document.getElementById('tipo').value;

		} else {

			telefone['ddd'] = document.getElementById('ddd').value;
			telefone['numero'] = document.getElementById('telefone').value;
			telefone['tipo'] = document.getElementById('tipo').value;

		}

		if (telefone.ddd == "" || telefone.numero == "" || telefone.tipo == "") {
			document.getElementById('modal-adicao-invalida').style.display = 'block';
		} else {

			telefones[indiceTelefone++] = telefone;
			if (tabelaTelefones == null) {
				var divTabela = document.getElementById('telefones');
				divTabela.innerHTML = '<table id=\"tabelaTelefones\" class=\"w3-table w3-bordered w3-striped w3-card-4\"><thead><tr class=\"w3-blue\"><th>DDD<\/th><th>N\u00FAmero<\/th><th>Tipo<\/th><th colspan=\"2\" style=\"width:10%;\">A\u00E7\u00F5es<\/th><\/tr><\/thead><\/table>';
				tabelaTelefones = document.getElementById('tabelaTelefones');
				tabelaTelefones.createTBody();
			}
			var corpoTabela = tabelaTelefones.tBodies[0];
			var linha = corpoTabela.insertRow(-1);

			if (edicao) {
				var iHidden = document.createElement('input');
				iHidden.type = "hidden";
				iHidden.value = telefone.id;
				linha.append(iHidden);
			}

			var celulaDdd = linha.insertCell(0);
			var celulaNumero = linha.insertCell(1);
			var celulaTipo = linha.insertCell(2);
			var celulaEditar = linha.insertCell(3);
			var celulaExcluir = linha.insertCell(4);

			celulaDdd.innerHTML = telefone.ddd;
			celulaNumero.innerHTML = telefone.numero;
			celulaTipo.innerHTML = telefone.tipo;
			celulaEditar.innerHTML = '<button type="button" class=\"w3-button w3-teal\" onclick="moduloManipulacao.editaTelefone(this)">Editar<\/button>';
			celulaExcluir.innerHTML = '<button type="button" class=\"w3-button w3-red\" onclick="moduloManipulacao.removeTelefone(this)">Remover<\/button>';

			if (edicao) {
				document.getElementById('idTelefone').value = null;
			}

			document.getElementById('ddd').value = null;
			document.getElementById('telefone').value = null;
			document.getElementById('tipo').selectedIndex = 0;
		}
	}

	function edita(gatilho) {
		var linha = gatilho.parentNode.parentNode;
		var telefone = telefones[linha.rowIndex - 1];

		if (edicao) {
			document.getElementById('idTelefone').value = telefone.id;
		}

		document.getElementById('ddd').value = telefone.ddd;
		document.getElementById('telefone').value = telefone.numero;
		document.getElementById('tipo').value = telefone.tipo;
		telefones.splice(linha.rowIndex - 1, 1);
		linha.parentNode.removeChild(linha);
		indiceTelefone--;
	}

	function envia() {
		var tabelaTelefones = document.getElementById('tabelaTelefones');
		var contato = {};

		if (edicao) {
			contato['id'] = document.getElementById('idContato').value;
		}

		contato['nome'] = document.getElementById('nome').value;

		if (contato.nome == null || contato.nome === "") {
		
			document.getElementById('modal-contato-invalido').style.display = 'block';
		
		} else {
			
			var form = edicao ? document.getElementById('formAtualiza')
					: document.getElementById('formNovo');

			var inputJson = document.getElementById('inputJson');
			if (inputJson == null) {
				inputJson = document.createElement('input');
				inputJson.type = 'hidden';
				inputJson.name = 'json';
				inputJson.id = 'inputJson';
				form.appendChild(inputJson);
			}
			contato['telefones'] = telefones;
			inputJson.value = JSON.stringify(contato);
			console.log('Elemento sendo submetido: ' + inputJson.value);
			form.submit();
		
		}

	}

	function remove(gatilho) {
		var linha = gatilho.parentNode.parentNode;
		telefones.splice(linha.rowIndex - 1, 1);
		linha.parentNode.removeChild(linha);
		indiceTelefone--;

		if (indiceTelefone == 0) {
			var divTabela = document.getElementById('telefones');
			divTabela.removeChild(divTabela.children[0]);
		}
	}

	return {
		adicionaTelefone : function() {
			adiciona();
		},
		editaTelefone : function(gatilho) {
			edita(gatilho);
		},
		enviaContato : function() {
			envia();
		},
		removeTelefone : function(gatilho) {
			remove(gatilho);
		}
	}

})();
