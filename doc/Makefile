#Author: Pavol Loffay

NAME=loffay
NAME2=mejzlik
NAME3=pentek

all: loffay mejzlik pentek

loffay:
	xsltproc --encoding utf-8 --xinclude -o $(NAME).fo /usr/share/sgml/docbook/xsl-ns-stylesheets/fo/docbook.xsl $(NAME).xml
	fop $(NAME).fo -pdf $(NAME).pdf

mejzlik:
	xsltproc --encoding utf-8 --xinclude -o $(NAME2).fo /usr/share/sgml/docbook/xsl-ns-stylesheets/fo/docbook.xsl $(NAME2).xml
	fop $(NAME2).fo -pdf $(NAME2).pdf

pentek:
	xsltproc --encoding utf-8 --xinclude -o $(NAME3).fo /usr/share/sgml/docbook/xsl-ns-stylesheets/fo/docbook.xsl $(NAME3).xml
	fop $(NAME3).fo -pdf $(NAME3).pdf


clean:
	rm -rf $(NAME).fo $(NAME).pdf $(NAME2).fo $(NAME2).pdf $(NAME3).fo $(NAME3).pdf
