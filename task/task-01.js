// Cria banco de dados
use vemserdbc



// Cria coleção
db.createCollection("log")



// Insere registros
db.log.insert(
    {
        "tipoLog" : "INFO",
        "idUsuario" : "2",
        "acao" : "usuarioService.fyndByLogin",
        "payload" : "{\"empty\":false,\"present\":true}",
        "data" : "2024-02-20T15:16:31.613+00:00",

    }
)
//retorno
{
        acknowledged: true,
            insertedIds: {
        '0': ObjectId('65d79ed3efa8105055edbb43')
}
}

db.log.insert(
    {
            "tipoLog" : "ERROR",
            "idUsuario" : "5",
            "acao" : "ProdutoService.fyndAll",
            "payload" : "{\"empty\":false,\"present\":true}",
            "data" : "2024-02-20T15:16:55.613+00:00",

    }
)
//retorno
{
        acknowledged: true,
            insertedIds: {
        '0': ObjectId('65d79f49efa8105055edbb44')
}
}

db.log.insert(
    {
            "tipoLog" : "ERROR",
            "idUsuario" : "7",
            "acao" : "PedidoService.add",
            "payload" : "{\"empty\":false,\"present\":true}",
            "data" : "2024-02-20T15:18:55.613+00:00",

    }
)
//retorno
{
        acknowledged: true,
            insertedIds: {
        '0': ObjectId('65d79f8cefa8105055edbb45')
}
}

db.log.insert(
    {
            "tipoLog" : "INFO",
            "idUsuario" : "1",
            "acao" : "PedidoService.getById",
            "payload" : "{\"empty\":false,\"present\":true}",
            "data" : "2024-02-20T15:11:55.555+00:00",

    }
)
//retorno
{
        acknowledged: true,
            insertedIds: {
        '0': ObjectId('65d79fd1efa8105055edbb46')
}
}

db.log.insert(
    {
            "tipoLog" : "INFO",
            "idUsuario" : "15",
            "acao" : "EnderecoService.getById",
            "payload" : "{\"empty\":false,\"present\":true}",
            "data" : "2024-02-20T15:11:55.555+00:00",

    }
)
//retorno
{
        acknowledged: true,
            insertedIds: {
        '0': ObjectId('65d7a00defa8105055edbb47')
}
}




// Busca registros
// Lista todos os log
db.log.find().pretty()
//retorno todos os logs
{
        _id: ObjectId('65d4c24e6752ae328f24057c'),
            tipoLog: 'INFO',
    idUsuario: 'Não autenticado',
    acao: 'UsuarioService.findByLogin(..)',
    payload: '{"empty":false,"present":true}',
    data: 2024-02-20T15:16:30.845Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4c2586752ae328f240583'),
            tipoLog: 'ERROR',
    idUsuario: '142',
    acao: 'UsuarioService.removerUsuario(..)',
    payload: 'Só é possivel retornar seus próprios dados.',
    data: 2024-02-20T15:16:40.553Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4c25a6752ae328f240585'),
            tipoLog: 'ERROR',
    idUsuario: '142',
    acao: 'UsuarioService.removerUsuario(..)',
    payload: 'Só é possivel retornar seus próprios dados.',
    data: 2024-02-20T15:16:42.090Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}


// Lista todos os logs do tipo info
db.log.find({tipoLog: "INFO" }).pretty()
//retorno todo os logs tipo info
{
        _id: ObjectId('65d4c2666752ae328f240594'),
            tipoLog: 'INFO',
    idUsuario: '142',
    acao: 'UsuarioService.getLoggedUser()',
    data: 2024-02-20T15:16:54.236Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4c2656752ae328f240593'),
            tipoLog: 'INFO',
    idUsuario: '142',
    acao: 'CarteiraService.obterSaldo()',
    payload: '{"idCarteira":33,"saldo":71.53}',
    data: 2024-02-20T15:16:53.436Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4c2636752ae328f240590'),
            tipoLog: 'INFO',
    idUsuario: '142',
    acao: 'UsuarioService.getLoggedUser()',
    data: 2024-02-20T15:16:51.999Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}

// Lista todos os logs de erros
db.log.find({ tipoLog:"ERROR" }).pretty()
//retorno de todos log de erros
{
        _id: ObjectId('65d79f8cefa8105055edbb45'),
            tipoLog: 'ERROR',
    idUsuario: '7',
    acao: 'PedidoService.add',
    payload: '{"empty":false,"present":true}',
    data: '2024-02-20T15:18:55.613+00:00'
}
{
        _id: ObjectId('65d79f49efa8105055edbb44'),
            tipoLog: 'ERROR',
    idUsuario: '5',
    acao: 'ProdutoService.fyndAll',
    payload: '{"empty":false,"present":true}',
    data: '2024-02-20T15:16:55.613+00:00'
}
{
        _id: ObjectId('65d4d3346752ae328f2405a7'),
            tipoLog: 'ERROR',
    idUsuario: '142',
    acao: 'UsuarioService.obterUsuarioPorId(..)',
    payload: 'Só é possivel retornar seus próprios dados.',
    data: 2024-02-20T16:28:36.960Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}



// Lista todos os logs de usuarios não autenticados
db.log.find({ idUsuario:"Não autenticado" }).pretty()
//retorno todos os logs de usuario não autenticados
{
        _id: ObjectId('65d4e5576752ae328f2405ab'),
            tipoLog: 'INFO',
    idUsuario: 'Não autenticado',
    acao: 'UsuarioService.findByLogin(..)',
    payload: '{"empty":false,"present":true}',
    data: 2024-02-20T17:45:59.975Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4e5406752ae328f2405aa'),
            tipoLog: 'INFO',
    idUsuario: 'Não autenticado',
    acao: 'UsuarioService.findByLogin(..)',
    payload: '{"empty":false,"present":true}',
    data: 2024-02-20T17:45:36.332Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4cd3df55ae443761a36da'),
            tipoLog: 'INFO',
    idUsuario: 'Não autenticado',
    acao: 'UsuarioService.findByLogin(..)',
    payload: '{"empty":false,"present":true}',
    data: 2024-02-20T16:03:09.966Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}

// Lista todos os logs de usuarios autenticados
db.log.find({idUsuario: {$ne:{idUsuario:"Não autenticado"}}})
// retorno de todos os logs de usuarios autenticados
{
        _id: ObjectId('65d4c2606752ae328f24058a'),
            tipoLog: 'INFO',
    idUsuario: '142',
    acao: 'UsuarioService.getLoggedUser()',
    data: 2024-02-20T15:16:48.885Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4c25d6752ae328f240589'),
            tipoLog: 'ERROR',
    idUsuario: '142',
    acao: 'UsuarioService.removerUsuario(..)',
    payload: 'Só é possivel retornar seus próprios dados.',
    data: 2024-02-20T15:16:45.020Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}
{
        _id: ObjectId('65d4c25c6752ae328f240588'),
            tipoLog: 'ERROR',
    idUsuario: '142',
    acao: 'UsuarioService.removerUsuario(..)',
    payload: 'Só é possivel retornar seus próprios dados.',
    data: 2024-02-20T15:16:44.265Z,
    _class: 'com.vemser.dbc.searchorganic.model.Log'
}