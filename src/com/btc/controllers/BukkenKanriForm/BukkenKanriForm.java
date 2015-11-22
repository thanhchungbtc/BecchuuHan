package com.btc.controllers.BukkenKanriForm;

import com.btc.controllers.BecchuuDetailsForm.BecchuuDetailsDelegate;
import com.btc.controllers.BecchuuDetailsForm.BecchuuDetailsForm;
import com.btc.controllers.BukkenDetailsForm.BukkenDetailsForm;
import com.btc.controllers.BukkenDetailsForm.BukkenDetailsFormDelegate;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import com.btc.viewModel.BecchuuTableModel;
import com.btc.viewModel.BukkenTableModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

public class BukkenKanriForm extends JFrame implements BukkenDetailsFormDelegate, BecchuuDetailsDelegate {

   private JPanel contentPane;
   private JTable bukkenTable;
   private JTable becchuuTable;
   private JLabel lblWelcome;

   private BukkenRepository bukkenRepository;
   private BecchuuRepository becchuuRepository;

   private JTextField txtBukkenSearch;
   TableRowSorter<TableModel> rowSorter;
   private JComboBox cbType;

   private void createAndSetupGUI() {
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);
      String[] columnNames1 = {"Title", "User Name", "Password", "URL", "Notes"};

      JPanel statusPanel = new JPanel();
      statusPanel.setBorder(null);
      contentPane.add(statusPanel, BorderLayout.SOUTH);
      statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

      lblWelcome = new JLabel("Welcome");
      statusPanel.add(lblWelcome);

      JPanel headePanel = new JPanel();
      headePanel.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(0, 0, 0, 0)));
      FlowLayout fl_headePanel = (FlowLayout) headePanel.getLayout();
      fl_headePanel.setAlignment(FlowLayout.LEFT);
      contentPane.add(headePanel, BorderLayout.NORTH);

      JLabel label = new JLabel("物件管理");
      label.setFont(new Font("Lucida Grande", Font.BOLD, 15));
      headePanel.add(label);

      JSplitPane mainSplitPane = new JSplitPane();
      mainSplitPane.setDividerSize(2);
      contentPane.add(mainSplitPane, BorderLayout.CENTER);

      JPanel leftPanel = new JPanel();
      mainSplitPane.setLeftComponent(leftPanel);
      leftPanel.setBorder(new EmptyBorder(0, 0, 0, 2));

      bukkenTable = new JTable();
      bukkenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      bukkenTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               bukkenTableSelectionChange(e);
            }

         }
      });
      leftPanel.setLayout(new BorderLayout(0, 0));


      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(bukkenTable);
      leftPanel.add(scrollPane);

      JPanel leftButtonPanel = new JPanel();
      leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);
      leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));

      btnAddGroup = new JButton();
      btnAddGroup.setContentAreaFilled(false);
      btnAddGroup.setBorderPainted(false);
      btnAddGroup.setFocusPainted(false);
      leftButtonPanel.add(btnAddGroup);
      btnAddGroup.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            btnAddBukkenActionPerformed(event);
         }
      });

      btnAddGroup.setIcon(new ImageIcon(BukkenKanriForm.class.getResource("/resources/icon/icon_plus.png")));

      JPanel searchBukkenPanel = new JPanel();
      searchBukkenPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
      leftPanel.add(searchBukkenPanel, BorderLayout.NORTH);

      txtBukkenSearch = new JTextField();
      txtBukkenSearch.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            txtBukkenSearchKeyReleased(e);
         }
      });
      searchBukkenPanel.setLayout(new BorderLayout(5, 0));
      searchBukkenPanel.add(txtBukkenSearch);
      txtBukkenSearch.setColumns(10);

      cbType = new JComboBox();
      cbType.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cbTypeActionPerformed(e);
         }
      });
      searchBukkenPanel.add(cbType, BorderLayout.EAST);


      JPanel rightPanel = new JPanel();
      mainSplitPane.setRightComponent(rightPanel);
      rightPanel.setBorder(new EmptyBorder(0, 2, 0, 0));

      becchuuTable = new JTable();

      becchuuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      rightPanel.setLayout(new BorderLayout(0, 0));

      JPanel becchuuSearchPanel = new JPanel();
      becchuuSearchPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
      rightPanel.add(becchuuSearchPanel, BorderLayout.NORTH);
      GridBagLayout gbl_becchuuSearchPanel = new GridBagLayout();
      gbl_becchuuSearchPanel.columnWidths = new int[]{103, 42, 28, 42, 28, 30, 28, 0};
      gbl_becchuuSearchPanel.rowHeights = new int[]{21, 0};
      gbl_becchuuSearchPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
      gbl_becchuuSearchPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
      becchuuSearchPanel.setLayout(gbl_becchuuSearchPanel);

      txtBecchuuSearch = new JTextField();
      GridBagConstraints gbc_txtBecchuuSearch = new GridBagConstraints();
      gbc_txtBecchuuSearch.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtBecchuuSearch.weightx = 1.0;
      gbc_txtBecchuuSearch.insets = new Insets(0, 0, 0, 5);
      gbc_txtBecchuuSearch.gridx = 0;
      gbc_txtBecchuuSearch.gridy = 0;
      becchuuSearchPanel.add(txtBecchuuSearch, gbc_txtBecchuuSearch);
      txtBecchuuSearch.setColumns(10);


      JScrollPane scrollPane_1 = new JScrollPane();
      scrollPane_1.setViewportView(becchuuTable);

      rightPanel.add(scrollPane_1);

      JPanel rightButtonPanel = new JPanel();
      rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);
      rightButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));

      JButton btnRefresh = new JButton();
      btnRefresh.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ((BecchuuTableModel) becchuuTable.getModel()).refresh();
         }
      });
      btnRefresh.setIcon(new ImageIcon(BukkenKanriForm.class.getResource("/resources/icon/icon-refresh.png")));
      btnRefresh.setFocusPainted(false);
      btnRefresh.setContentAreaFilled(false);
      btnRefresh.setBorderPainted(false);
      rightButtonPanel.add(btnRefresh);

   }

   private void initializeData() {
      bukkenRepository = new BukkenRepository();
      becchuuRepository = new BecchuuRepository();
   }

   private void setupTable() {

      BukkenTableModel bukkenTableModel = new BukkenTableModel(this.bukkenRepository);
      bukkenTable.setModel(bukkenTableModel);
      rowSorter = new TableRowSorter<TableModel>(bukkenTable.getModel());
      bukkenTable.setRowSorter(rowSorter);
      bukkenTable.setRowHeight(25);
      bukkenTable.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseMoved(MouseEvent e) {
            Point point = e.getPoint();
            if (bukkenTable.columnAtPoint(point) == bukkenTable.getColumnCount() - 1) {
               bukkenTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
               bukkenTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
         }
      });
      bukkenTable.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
               bukkenTableClicked(e);
            } else if (e.getClickCount() == 2) {
               bukkenTableDoubleClicked(e);
            }
         }
      });

      becchuuTable.setRowHeight(25);
      BecchuuTableModel becchuuTableModel = new BecchuuTableModel(becchuuRepository);
      becchuuTable.setModel(becchuuTableModel);
   }

   private void loadShouhinTypeCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(bukkenRepository.getBukkenTypes()
            .toArray());
      model.insertElementAt("商品タイプ", 0);
      cbType.setModel(model);
      cbType.setSelectedIndex(0);
   }

   // handle events--------------------------------------------------------------------------------------
   private void btnAddBukkenActionPerformed(ActionEvent event) {
      BukkenDetailsForm form = new BukkenDetailsForm(null);
      form.setLocationRelativeTo(this);
      form.showDialog(this);
   }

   private void bukkenTableClicked(MouseEvent event) {
      if (bukkenTable.getSelectedRow() == -1) return;
      int col = bukkenTable.getSelectedColumn();
      if (col != bukkenTable.getColumnCount() - 1) return;

      int row = bukkenTable.getSelectedRow();
      String id = bukkenTable.getValueAt(row, 1).toString();
      Bukken bukken = bukkenRepository.getBukkenWithID(id);
      String link = bukken.getBecchuuDBURL();
      Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
      if (desktop != null) {
         try {
            desktop.browse(new URI(link));
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   private void bukkenTableSelectionChange(ListSelectionEvent e) {
      int row = bukkenTable.getSelectedRow();
      if (row < 0) return;
      Bukken bukken = null;
      String id = bukkenTable.getValueAt(row, 1).toString();
      bukken = bukkenRepository.getBukkenWithID(id);

      loadBecchuuTableWithSelectedBukken(bukken);
   }

   private void bukkenTableDoubleClicked(MouseEvent event) {
      if (Config.RoleID.equals("IP")) return;
      if (bukkenTable.getSelectedRow() == -1) return;
      int row = bukkenTable.getSelectedRow();
      String id = bukkenTable.getValueAt(row, 1).toString();
      Bukken bukkenToEdit = bukkenRepository.getBukkenWithID(id);
      // if found bukken at selectedRow
      if (bukkenToEdit != null) {
         BukkenDetailsForm form = new BukkenDetailsForm(bukkenToEdit);
         form.setLocationRelativeTo(this);
         form.showDialog(this);
      }
   }

   private void loadBecchuuTableWithSelectedBukken(Bukken bukken) {
      ((BecchuuTableModel) becchuuTable.getModel()).setBukken(bukken);
   }

   // begin: search on bukkenTable
   RowFilter typeFilter;
   RowFilter textFilter;
   LinkedList<RowFilter<TableModel, Object>> rowFilters = new LinkedList<>();
   private JTextField txtBecchuuSearch;
   private JButton btnAddGroup;

   private void filterBukkenTable() {
      rowFilters.clear();
      if (textFilter != null) {
         rowFilters.add(textFilter);
      }
      if (typeFilter != null) {
         rowFilters.add(typeFilter);
      }
      rowSorter.setRowFilter(RowFilter.andFilter(rowFilters));
   }

   private void txtBukkenSearchKeyReleased(KeyEvent e) {
      if (!(e.getSource() instanceof JTextField)) return;
      JTextField source = (JTextField) e.getSource();
      if (source == txtBukkenSearch) {
         if (source.getText().trim().length() == 0) {
            textFilter = null;
         } else {
            String regex = Helpers.convertGlobToRegExCaseInsensitive(source.getText());
            textFilter = RowFilter.regexFilter(regex, 0, 1, 2);
         }
      }
      filterBukkenTable();
   }

   private void cbTypeActionPerformed(ActionEvent event) {
      Object source = event.getSource();
      if (source == cbType) {
         if (cbType.getSelectedIndex() == 0) {
            typeFilter = null;
         } else {
            typeFilter = RowFilter.regexFilter(cbType.getModel().getSelectedItem().toString(), 3);
         }
      }
      filterBukkenTable();
   }

   // end: search on bukkenTable

   // end handle events------------------------------------------------------------------------------------

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      Config.setLookAndField();

      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               BukkenKanriForm frame = new BukkenKanriForm();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   private void loadFromDataBase() {
      initializeData();
      setupTable();
   }

   /**
    * Create the frame.
    */
   public BukkenKanriForm() {
      setTitle("別注管理  ― ハノイ支店");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // setMinimumSize(new Dimension(850, 700));

      createAndSetupGUI();
      loadFromDataBase();
      loadShouhinTypeCombobox();

      // set up role
      if (Config.RoleID.equals("IP")) {
         btnAddGroup.setEnabled(false);
      }
      lblWelcome.setText("Welcome " + Config.UserName);
   }

   // BEGIN implements BukkenDetailFormsDelegate--------------------------------------------
   @Override
   public void submitData(Bukken bukken, boolean insert) {
      BukkenTableModel bukkenTableModel = (BukkenTableModel) bukkenTable.getModel();
      if (insert) {
         bukkenTableModel.insertBukken(bukken);

      } else {
         bukkenTableModel.updateBukken(bukken, bukkenTable.getSelectedRow());

      }
   }

   @Override
   public void submitData(BecchuuDetailsForm becchuuDetailsForm, Becchuu becchuu) {

   }

   @Override
   public void movePrevious(BecchuuDetailsForm becchuuDetailsForm) {

   }

   @Override
   public void moveNext(BecchuuDetailsForm becchuuDetailsForm) {

   }

   // END implements BukkenDetailFormsDelegate--------------------------------------------

}
