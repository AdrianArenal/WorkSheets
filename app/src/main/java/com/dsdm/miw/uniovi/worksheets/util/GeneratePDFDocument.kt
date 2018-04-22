package com.dsdm.miw.uniovi.worksheets.util


import android.graphics.Bitmap
import com.itextpdf.io.IOException
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import java.text.SimpleDateFormat
import java.util.*
import com.itextpdf.io.image.ImageDataFactory
import java.io.ByteArrayOutputStream


class GeneratePDFDocument {

    @Throws(IOException::class)
    fun createPdf(dest : String, customer : String, worker : String,
                  start : Date, end: Date, desc : String, image : Bitmap) {
        val writer = PdfWriter(dest)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)
        val startDate = SimpleDateFormat("dd/MM/yyyy hh:mm").format(start)
        val endDate = SimpleDateFormat("dd/MM/yyyy hh:mm").format(end)
        document.add(Paragraph("***** PARTE DE TRABAJO *****"))
        document.add(Paragraph("CLIENTE: $customer"))
        document.add(Paragraph("TRABAJADOR: $worker"))
        document.add(Paragraph("----- DATOS DE LA VISITA ----"))
        document.add(Paragraph("HORA DE INICIO: $startDate"))
        document.add(Paragraph("HORA DE FINALIZACIÓN: $endDate"))
        document.add(Paragraph("DESCRIPCIÓN: $desc"))
        document.add(Paragraph("-----------------------------"))
        document.add(Paragraph("FIRMA: "))
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val image = Image(ImageDataFactory.create(stream.toByteArray()))
        image.scaleToFit(100f, 100f)
        document.add(image)
        document.close()
    }
}