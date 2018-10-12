################################################################################
# Author: João Pedro de Amorim Paula, Max William S. Filgueira                 #
################################################################################

# Common Properties
SRCDIR = ./src
OBJDIR = classes
JAVA_HOME = /usr/local/jdk
JAVAC = $(JAVA_HOME)/bin/javac
JAR = $(JAVA_HOME)/bin/jar
JVM_OPTION = -g
CLASSPATH = "./src:./classes"
PKG = hxh
RES_EXT = properties xml

# Java Compiler Args
JAVACFLAGS := -bootclasspath $(JAVA_HOME)/jre/lib/rt.jar \
	-classpath $(CLASSPATH) \
	-sourcepath $(SRCDIR) \
	-d $(OBJDIR) \
	$(JVM_OPTION)

# Sources
SOURCES = $(shell find $(SRCDIR) \
	-name *.java \
	$(foreach x, $(RES_EXT), -o -name "*.$(x)"))
# Obj files
OBJS = $(patsubst $(SRCDIR)/%, $(OBJDIR)/%, $(patsubst %.java, %.class, $(SOURCES)))

.PHONY: all run compile jar clean rebuild

# Default target
all : jar

# run
run : jar
	$(JAVA_HOME)/bin/java -cp $(PKG).jar hxh.Simulator

# Jar
jar : compile $(PKG)
$(PKG) : $(OBJS)
	$(JAR) cvf $(PKG).jar -C $(OBJDIR) .

# Compile
compile : $(OBJDIR) $(OBJS)
# Javac
$(OBJDIR)/%.class : $(SRCDIR)/%.java
	@echo compile $<
	@$(JAVAC) $(JAVACFLAGS) $<
ifneq ($(OBJDIR),$(SRCDIR))
# Resource file
$(OBJDIR)/% : $(SRCDIR)/%
	@mkdir -p $(dir $@)
	cp $< $@
endif


# Create obj directory
$(OBJDIR) :
	mkdir -p $(OBJDIR)

# Clean up
clean :
	rm -fr $(PKG)
ifeq ($(OBJDIR),$(SRCDIR))
	find $(OBJDIR) -name *.class -exec rm {} \;
else
	rm -fr $(OBJDIR)
endif

# Rebuild
rebuild : clean jar
