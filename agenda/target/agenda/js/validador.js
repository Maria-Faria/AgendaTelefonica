function validar() {
    let name = frmContato.name.value
    let tel = frmContato.tel.value

    if(name === "") {
        alert('Preencha o campo NOME')
        frmContato.name.focus()
        return false
    
    }else if(tel === "") {
        alert('Preencha o campo TELEFONE')
        frmContato.tel.focus()
        return false

    }else {
        document.forms["frmContato"].submit()
    }
}