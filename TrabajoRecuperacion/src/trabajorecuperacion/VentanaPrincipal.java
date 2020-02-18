
package trabajorecuperacion;

import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author Jhonier
 */
public class VentanaPrincipal extends javax.swing.JFrame {

     static OntModel model;
     DefaultListModel ModeloListaClases;
     DefaultListModel ModeloListaClases2;
     DefaultListModel ModeloListaInstancias;
      DefaultListModel ModeloListaInstancias2;
     DefaultListModel ModeloListaRelaciones;
     DefaultListModel ModeloListaAtributos;
     DefaultListModel ModeloListaIgualValorAtributo;
     DefaultListModel ModeloListaValores;
     DefaultListModel ModeloInstanciasRelaciones;
     DefaultListModel ModeloInstanciasHijos;
     DefaultListModel ModeloFiltro1;
     OntClass ClaseActual;
     ArrayList<OntClass> Clases = new ArrayList<>();
     ArrayList<Individual> Instancias = new ArrayList<>();
     ArrayList<Statement> Atributos = new ArrayList<>();
     
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal(OntModel modelo) {
        initComponents();
        ModeloListaClases = new DefaultListModel();
        ModeloListaClases2 = new DefaultListModel();
        ModeloListaInstancias = new DefaultListModel();
        ModeloListaInstancias2 = new DefaultListModel();
        ModeloListaRelaciones = new DefaultListModel();
        ModeloListaAtributos = new DefaultListModel();
        ModeloListaIgualValorAtributo = new DefaultListModel();
        ModeloListaValores = new DefaultListModel();
        ModeloInstanciasRelaciones = new DefaultListModel();
        ModeloInstanciasHijos = new DefaultListModel();
        ModeloFiltro1 = new DefaultListModel();
        VentanaListaClases.setModel(ModeloListaClases);
        //VentanaListaClases2.setModel(ModeloListaClases2);
        VentanaListaInstancias.setModel(ModeloListaInstancias);
        //VentanaListaInstancias2.setModel(ModeloListaInstancias2);
        VentanaListaRelaciones.setModel(ModeloListaRelaciones);
        VentanaListaAtributos.setModel(ModeloListaAtributos);
        VentanaListaValores.setModel(ModeloListaValores);
        VentanaInstanciasRelaciones.setModel(ModeloInstanciasRelaciones);
        VentanaInstanciasHijos.setModel(ModeloInstanciasHijos);
        VentanaFiltro1.setModel(ModeloFiltro1);
        VentanaListaIgualValorAtributo.setModel(ModeloListaIgualValorAtributo);
        DatosListaClases(modelo);
        
    }
    
    public  void DatosListaClases(OntModel modelo)
    {
        
        
         ExtendedIterator iter = modelo.listClasses();
        try{
            while (iter.hasNext()){
                OntClass stmt = (OntClass) iter.next();
                Clases.add(stmt);
                ModeloListaClases.addElement(stmt.getLocalName());
                ModeloListaClases2.addElement(stmt);
            }
        }finally{
            iter.close();
        }
    }
    
    /*public void RelacionEntreInstancias(){
        //Obtener Instancia 1 seleccionada
        int pos = VentanaListaInstancias.getSelectedIndex();
        Individual ind = (Individual) ModeloListaInstancias.get(pos);
        
        //Obtener Instancia2 seleccionada
        int pos2 = VentanaListaInstancias2.getSelectedIndex();
        Individual ind2 = (Individual) ModeloListaInstancias2.get(pos2);
        
        StmtIterator result = model.listStatements(ind, null, ind2);
        while(result.hasNext()){
            Statement s = result.next();
            //System.out.println(s.getSubject() + "Recurso");
            /*if (s.getPredicate().canAs(DatatypeProperty.class)) {
            System.out.println(s.getPredicate().toString()+"  "+ s.getObject().toString());
            ModeloListaAtributos.addElement(s);//(s.getPredicate()); //+ s.getObject()
            } */
            //System.out.println(s.getSubject());
            //ModeloListaIgualValorAtributo.addElement(s.getSubject()); */
            /*System.out.println("Hay relacion");
            }   
        
        
    } */
    
    public void MostrarInstancias(){
        ModeloListaInstancias.clear();
        Instancias.clear();
        int pos = VentanaListaClases.getSelectedIndex();
        OntClass ElementoSeleccionado = Clases.get(pos);
        ExtendedIterator instancias = ElementoSeleccionado.listInstances();
         while (instancias.hasNext())
      {
        Individual thisInstance = (Individual) instancias.next();
        Instancias.add(thisInstance);
        ModeloListaInstancias.addElement(thisInstance.getLocalName());
      }
        
    }
    
   /* public void MostrarInstancias2(){
        ModeloListaInstancias2.clear();
        int pos = VentanaListaClases2.getSelectedIndex();
        OntClass ElementoSeleccionado = (OntClass) ModeloListaClases2.get(pos);
        //ModeloListaInstancias.addElement("Hola");
        ExtendedIterator instancias = ElementoSeleccionado.listInstances();
         while (instancias.hasNext())
      {
        Individual thisInstance = (Individual) instancias.next();
        ModeloListaInstancias2.addElement(thisInstance);
      }
        
    }*/
    
    public void MostrarRelaciones(){
        ModeloListaRelaciones.clear();
        ModeloInstanciasRelaciones.clear();
        int pos = VentanaListaInstancias.getSelectedIndex();
        Individual ind =  Instancias.get(pos);
        StmtIterator listapropiedades = ind.listProperties();
        while(listapropiedades.hasNext()){
            Statement s = listapropiedades.next();
            
            //System.out.println(s.getSubject() + "Recurso");
            if (s.getPredicate().canAs(ObjectProperty.class)) {
            //System.out.println(s.getPredicate().toString()+"  "+ s.getObject().toString());
            ModeloListaRelaciones.addElement(s.getPredicate().getLocalName());
            //ModeloListaValores.addElement(s.getObject().asLiteral().getValue());
            ModeloInstanciasRelaciones.addElement(s.getObject());
            } 
            }
        
    }
    
    public void MostrarAtributosClase(){
        ModeloListaAtributos.clear();
        int pos = VentanaListaClases.getSelectedIndex();
        OntClass ElementoSeleccionado = (OntClass) ModeloListaClases.get(pos);
        ExtendedIterator<Statement> iter = ElementoSeleccionado.listProperties();
        ResIterator it = model.listSubjects();
        //ExtendedIterator it = ElementoSeleccionado.listDeclaredProperties();
        while(iter.hasNext()){
            Statement r =  iter.next();
            System.out.println(r);
        }
    }
    
    
    public void MostrarAtributos(){
        ModeloListaAtributos.clear();
        Atributos.clear();
        ModeloListaValores.clear();
        int pos = VentanaListaInstancias.getSelectedIndex();
        Individual ind = Instancias.get(pos);
        StmtIterator listapropiedades = ind.listProperties();
        while(listapropiedades.hasNext()){
            Statement s = listapropiedades.next();
            //System.out.println(s.getSubject() + "Recurso");
            if (s.getPredicate().canAs(DatatypeProperty.class)) {
            Atributos.add(s);
            ModeloListaAtributos.addElement(s.getPredicate().getLocalName());
            ModeloListaValores.addElement(s.getObject().asLiteral().getValue());
            ;//(s.getPredicate()); //+ s.getObject()
            } 
            }
        
    }
    
    public void MostrarInstanciasHijos(){
        ModeloInstanciasHijos.clear();
        int posi = VentanaListaClases.getSelectedIndex();
        OntClass ElementoSeleccionado = Clases.get(posi);
        ExtendedIterator iter = ElementoSeleccionado.listSubClasses();
        while(iter.hasNext()){
            OntClass subclase = (OntClass) iter.next();
            ExtendedIterator instancias = subclase.listInstances();
         while (instancias.hasNext())
      {
        Individual thisInstance = (Individual) instancias.next();
        //Instancias.add(thisInstance);
        ModeloInstanciasHijos.addElement(thisInstance.getLocalName());
      }
        }
    }
    
    public void Filtro1(){
        ModeloFiltro1.clear();
        int pos = VentanaListaAtributos.getSelectedIndex();

        Statement ind = Atributos.get(pos);
        String valorfiltrado = CampoFiltro1.getText();
        if (!valorfiltrado.isEmpty()){
            
        
        StmtIterator it = model.listStatements(
                new SimpleSelector(null,ind.getPredicate(),(RDFNode) null){
                    public boolean selects(Statement s){
                        return s.getString().contains(valorfiltrado);
                    }
                });
        while(it.hasNext()){
            Statement result = it.next();
            ModeloFiltro1.addElement(result.getSubject().getLocalName());
        }
                    }}
    
    
    public void filtro2(){
        int pos = VentanaListaAtributos.getSelectedIndex();

        Statement ind = Atributos.get(pos);
        String valorfiltrado = CampoFiltro1.getText();
        if(valorfiltrado.isEmpty()){
            StmtIterator it = model.listStatements(null,ind.getPredicate(),(RDFNode) null);
            int maximo = 0;
            int minimo = 429496729;
            int conteo = 0;
            int suma = 0;
        while(it.hasNext()){
            Statement result = it.next();
            
            conteo +=1;
            suma += result.getObject().asLiteral().getString().length();
            if(result.getObject().asLiteral().getInt() > maximo){
                        maximo = result.getObject().asLiteral().getInt();
                    }
                    if(result.getObject().asLiteral().getInt()< minimo){
                        minimo = result.getObject().asLiteral().getInt();
                    }
            
        }
        jTextArea1.setText("Cantidad de instancias = "+ conteo + "\n" + "maximo = "+ maximo + "\n"
            + "minimo = "+ minimo + "\n" + "promedio = "+ suma/conteo);
    }
        else if(Condicionales.getSelectedItem() == "Mayor que"){
            StmtIterator it = model.listStatements(null,ind.getPredicate(),(RDFNode)null);
            
            int conteo = 0;
            int maximo = 0;
            int minimo = 429496729;
            int suma = 0;
            
            while(it.hasNext()){
                Statement result = it.next();
                
                if(result.getObject().asLiteral().getInt() > Integer.parseInt(valorfiltrado) ){
                    conteo += 1;
                    suma += result.getObject().asLiteral().getInt();
                    if(result.getObject().asLiteral().getInt() > maximo){
                        maximo = result.getObject().asLiteral().getInt();
                    }
                    if(result.getObject().asLiteral().getInt()< minimo){
                        minimo = result.getObject().asLiteral().getInt();
                    }
                }
            }
            jTextArea1.setText("Cantidad de instancias = "+ conteo + "\n" + "maximo = "+ maximo + "\n"
            + "minimo = "+ minimo + "\n" + "promedio = "+ suma/conteo);
        }
        else if(Condicionales.getSelectedItem() == "Menor que"){
        
            StmtIterator it = model.listStatements(null,ind.getPredicate(),(RDFNode)null);
             int conteo = 0;
             int maximo = 0;
             int minimo = 429496729;
             int suma = 0;
            while(it.hasNext()){
                Statement result = it.next();
           
                if(result.getObject().asLiteral().getInt() < Integer.parseInt(valorfiltrado) ){
                    conteo += 1;
                    suma += result.getObject().asLiteral().getInt();
                    if(result.getObject().asLiteral().getInt() > maximo){
                        maximo = result.getObject().asLiteral().getInt();
                    }
                    if(result.getObject().asLiteral().getInt()< minimo){
                        minimo = result.getObject().asLiteral().getInt();
                    }
                }
            }
            jTextArea1.setText("Cantidad de instancias = "+ conteo + "\n" + "\nmaximo = "+ maximo + "\n"
            + "minimo = "+ minimo + "\n" + "promedio = "+ suma/conteo);
            
    }else{
            
             StmtIterator it = model.listStatements(
                new SimpleSelector(null,ind.getPredicate(),(RDFNode) null){
                    public boolean selects(Statement s){
                        return s.getString().contains(valorfiltrado);
                    }
                });
            int conteo = 0;
            int suma = 0;
        while(it.hasNext()){
            Statement result = it.next();
            
            conteo +=1;
            suma += result.getObject().asLiteral().getString().length();
            
        }
        jTextArea1.setText("Cantidad de instancias = "+ conteo + "\n" + "Longitud promedio de cadena = "+ suma/conteo);
            
        }
    }
                

    
    public void IgualValorAtributo(){
        ModeloListaIgualValorAtributo.clear();
        //obtener el individual del atributo
        int posa = VentanaListaInstancias.getSelectedIndex();
        Individual ind = (Individual) Instancias.get(posa);
        //obtener clase del individual
        OntClass clase = ind.getOntClass();
        //obtenemos el atributo seleccionado del individual
        int pos = VentanaListaAtributos.getSelectedIndex();
        Statement st = (Statement) Atributos.get(pos);
        Property propiedad = (Property) st.getPredicate();
        StmtIterator result = model.listStatements(null, propiedad, st.getObject());
          while(result.hasNext()){
            Statement s = result.next();
            ModeloListaIgualValorAtributo.addElement(s.getSubject().getLocalName());
            }    
    }
    
    public void EstadisticasPorAtributo(){
        //obtener el individual del atributo
        int posa = VentanaListaInstancias.getSelectedIndex();
        Individual indactual = Instancias.get(posa);
         
         //obtenemos el atributo seleccionado del individual
        int pos = VentanaListaAtributos.getSelectedIndex();
        Statement st = (Statement) Atributos.get(pos);
        Property propiedad = (Property) st.getPredicate();
        
        //st.getObject().asLiteral()
        
        int posi = VentanaListaClases.getSelectedIndex();
        OntClass ElementoSeleccionado = (OntClass) Clases.get(posi);
        //ModeloListaInstancias.addElement("Hola");
        ExtendedIterator instancias = ElementoSeleccionado.listInstances();
        int conteo = 0;
        while(instancias.hasNext()){
            //contar instancias
            
            Individual Inst =  (Individual) instancias.next();
           StmtIterator stm =  model.listStatements(Inst,propiedad,(RDFNode)null);
            while(stm.hasNext()){
                Statement result = stm.next();
                conteo += 1;
            }
        }

                
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VentanaListaClases = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        VentanaListaInstancias = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        VentanaListaAtributos = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        VentanaListaValores = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        VentanaListaRelaciones = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        VentanaInstanciasRelaciones = new javax.swing.JList();
        IndirectoHijos = new javax.swing.JCheckBox();
        jScrollPane8 = new javax.swing.JScrollPane();
        VentanaInstanciasHijos = new javax.swing.JList();
        CampoFiltro1 = new javax.swing.JTextField();
        BotonFiltro1 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        VentanaFiltro1 = new javax.swing.JList();
        jScrollPane11 = new javax.swing.JScrollPane();
        VentanaListaIgualValorAtributo = new javax.swing.JList();
        Condicionales = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        VentanaListaClases.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clases", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        VentanaListaClases.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        VentanaListaClases.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VentanaListaClasesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(VentanaListaClases);

        VentanaListaInstancias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instancias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        VentanaListaInstancias.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        VentanaListaInstancias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VentanaListaInstanciasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(VentanaListaInstancias);

        VentanaListaAtributos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atributos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        VentanaListaAtributos.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        VentanaListaAtributos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VentanaListaAtributosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(VentanaListaAtributos);

        VentanaListaValores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "valores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        VentanaListaValores.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(VentanaListaValores);

        VentanaListaRelaciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Relacion ->", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        VentanaListaRelaciones.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(VentanaListaRelaciones);

        VentanaInstanciasRelaciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Valor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        VentanaInstanciasRelaciones.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(VentanaInstanciasRelaciones);

        IndirectoHijos.setText("Indirecto");
        IndirectoHijos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IndirectoHijosActionPerformed(evt);
            }
        });

        VentanaInstanciasHijos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instancias Hijos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jScrollPane8.setViewportView(VentanaInstanciasHijos);

        BotonFiltro1.setText("Filtrar");
        BotonFiltro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonFiltro1ActionPerformed(evt);
            }
        });

        VentanaFiltro1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instancias Filtradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jScrollPane10.setViewportView(VentanaFiltro1);

        VentanaListaIgualValorAtributo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Valor Igual de atributo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jScrollPane11.setViewportView(VentanaListaIgualValorAtributo);

        Condicionales.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mayor que", "Menor que", "Contiene" }));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Condicionales, 0, 91, Short.MAX_VALUE)
                            .addComponent(CampoFiltro1)
                            .addComponent(BotonFiltro1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(59, 59, 59))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IndirectoHijos, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(336, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(IndirectoHijos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Condicionales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CampoFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BotonFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jScrollPane9.setViewportView(jPanel1);

        jTabbedPane2.addTab("Punto 1", jScrollPane9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VentanaListaInstanciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VentanaListaInstanciasMouseClicked
        MostrarAtributos();
        MostrarRelaciones();
    }//GEN-LAST:event_VentanaListaInstanciasMouseClicked

    private void VentanaListaClasesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VentanaListaClasesMouseClicked
        MostrarInstancias();
        if(IndirectoHijos.isSelected()){
            MostrarInstanciasHijos();
        }
    }//GEN-LAST:event_VentanaListaClasesMouseClicked

    private void IndirectoHijosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IndirectoHijosActionPerformed
        if(IndirectoHijos.isSelected()){
            MostrarInstanciasHijos();
        }
        else{
            ModeloInstanciasHijos.clear();
        }
    }//GEN-LAST:event_IndirectoHijosActionPerformed

    private void VentanaListaAtributosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VentanaListaAtributosMouseClicked
        IgualValorAtributo();
        //EstadisticasPorAtributo();
    }//GEN-LAST:event_VentanaListaAtributosMouseClicked

    private void BotonFiltro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonFiltro1ActionPerformed
        Filtro1();
        filtro2();
    }//GEN-LAST:event_BotonFiltro1ActionPerformed

    
    public static void main(String args[]) {
        
    //Cargar archivo Robots
         model = ModelFactory.createOntologyModel();
        
        InputStream in = FileManager.get().open("ontologiaIntegracion4.owl");
        model.read(in,null,"RDF/XML");
        
        

    /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal(model).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonFiltro1;
    private javax.swing.JTextField CampoFiltro1;
    private javax.swing.JComboBox Condicionales;
    private javax.swing.JCheckBox IndirectoHijos;
    private javax.swing.JList VentanaFiltro1;
    private javax.swing.JList VentanaInstanciasHijos;
    private javax.swing.JList VentanaInstanciasRelaciones;
    private javax.swing.JList VentanaListaAtributos;
    private javax.swing.JList VentanaListaClases;
    private javax.swing.JList VentanaListaIgualValorAtributo;
    private javax.swing.JList VentanaListaInstancias;
    private javax.swing.JList VentanaListaRelaciones;
    private javax.swing.JList VentanaListaValores;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
