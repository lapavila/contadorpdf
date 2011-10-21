package br.com.patrius.contadorpdf;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Hello world!
 *
 */
public class App {

	public static void main( String[] args ) {
		if (args.length == 0) {
			System.out.println( "Como usar:\n$ java -jar contadorpdf-[version].jar /path/para/arquivos/pdf" );
		} else {
			System.out.println("======================================================================");
			System.out.println("Contador de páginas de Arquivos PDF - Pátrius Soluções");
			String path = args[0];
			File diretorio = new File(path);
			Long quantidadeArquivos = 0L;
			Long quantidadePagina = 0L;

			if (diretorio.isDirectory()) {
				System.out.println("Contando a Quantidade de páginas por arquivo PDF da pasta:\n" + diretorio.getAbsolutePath());
				System.out.println("======================================================================");
				FileFilter fileFilter = new FileFilter() { 
					public boolean accept(File b){ 
						return b.getName().endsWith(".pdf"); 
					} 
				};
				
				for (File file : diretorio.listFiles(fileFilter)) {
					System.out.print("Arquivo: " + file.getName());
					FileInputStream pdfInputStrem = null;
					PDDocument document = null;
					PDFParser parser = null;
					try {
						pdfInputStrem = new FileInputStream(file);
						parser = new PDFParser(pdfInputStrem);
						parser.parse();
						document = parser.getPDDocument();
						int paginas = document.getNumberOfPages();
						quantidadePagina += paginas;
						System.out.println(" => " + paginas + " paginas.");
						quantidadeArquivos++;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (document != null) {
								document.close();
							}
							if (pdfInputStrem != null) {
								pdfInputStrem.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				System.out.println("======================================================================");
				System.out.println("Quantidade de Arquivos: " + quantidadeArquivos);
				System.out.println("Quantidade de Paginas.: " + quantidadePagina);
			} else {
				System.out.println( "O caminho informado nao e um diretorio" );
			}

		}
	}
}
