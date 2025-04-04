package com.dieyteixeira.fluxsync.app.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.dieyteixeira.fluxsync.app.di.model.Relatorio
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("MemberExtensionConflict")
fun exportarTransacoesParaCSV(context: Context, relatorio: List<Relatorio>): File? {
    return try {
        val nomeArquivo = "relatorio_transacoes.csv"
        val diretorio = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val arquivo = File(diretorio, nomeArquivo)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        FileOutputStream(arquivo).use { fos ->
            fos.write(0xEF)
            fos.write(0xBB)
            fos.write(0xBF)

            OutputStreamWriter(fos, Charsets.UTF_8).use { writer ->
                writer.append("Descrição,Valor,Tipo,Situação,Categoria,Subcategoria,Conta,Data,Lançamento,Parcelas,Observação\n")
                relatorio.forEach { transacao ->
                    writer.append("${transacao.descricao},")
                    writer.append("${transacao.valor},")
                    writer.append("${transacao.tipo},")
                    writer.append("${transacao.situacao},")
                    writer.append("${transacao.categoria},")
                    writer.append("${transacao.subcategoria},")
                    writer.append("${transacao.conta},")
                    writer.append("${dateFormat.format(transacao.data)},")
                    writer.append("${transacao.lancamento},")
                    writer.append("${transacao.parcelas},")
                    writer.append("${transacao.observacao}\n")
                }
            }
        }
        arquivo
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun enviarEmailComRelatorio(context: Context, arquivo: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        arquivo
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/csv"
        putExtra(Intent.EXTRA_EMAIL, arrayOf("dieinison.teixeira@gmail.com"))
        putExtra(Intent.EXTRA_SUBJECT, "Relatório de Transações")
        putExtra(Intent.EXTRA_TEXT, "Segue em anexo o relatório das suas transações.")
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Enviar relatório por e-mail"))
}