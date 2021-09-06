
#Makefile for Ass 1
#Rayhaan Omar
#Sometime in Aug

JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOC=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
CLASSES= Score.class WordDictionary.class WordRecord.class WordPanel.class WordApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)
SOURCES= Score.java WordDictionary.java WordRecord.java WordPanel.java WordApp.java
SOURCE_FILES= $(SOURCES:%.java=$(SRCDIR)/%.java)

default: $(CLASS_FILES)

clean: 	
	rm $(BINDIR)/*.class
runseq: $(CLASS_FILES)
	java -cp $(BINDIR) WordApp
	@echo  $("sampleInput100.txt 3 output.txt")

doc: $(SOURCE_FILES)
	javadoc -version -author -d $(DOC) $(SOURCE_FILES)
