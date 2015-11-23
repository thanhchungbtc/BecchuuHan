package com.btc.controllers.BecchuuKanriForm;

import com.btc.controllers.BecchuuDetailsForm.BecchuuDetailsDelegate;
import com.btc.controllers.BecchuuDetailsForm.BecchuuDetailsForm;
import com.btc.model.Becchuu;
import com.btc.model.BecchuuType;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.supports.Config;
import com.btc.supports.Helpers;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class BecchuuKanriForm extends JFrame implements BecchuuDetailsDelegate {

   private BecchuuRepository becchuuRepository;
   TableRowSorter<TableModel> rowSorter;
   BecchuuDetailsForm becchuuDetailsForm;

   /**
    * Create the frame.
    */
   public BecchuuKanriForm() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("別注管理");
//      setBounds(100, 100, 800, 600);
      createAndSetUpGUI();
      initializeData();
      loadBecchuuEmployeesCombobox();
      loadBecchuuTypeCombobox();
      setupTable();

      regisEventHandler();
      filterBecchuuTable();
      pack();
   }

   private void loadBecchuuTypeCombobox() {
      DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
      List<BecchuuType> becchuuTypes = CommonRepository.getBecchuuTypes();
      model.addElement("全て");
      for (BecchuuType becchuuType : becchuuTypes) {
         model.addElement(becchuuType.getName());
      }
      cbBecchuuType.setModel(model);
      cbBecchuuType.setSelectedIndex(0);
   }

   private void loadBecchuuEmployeesCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees()
          .toArray());
      model.insertElementAt("全て", 0);
      cbSakuseiSha.setModel(model);


      DefaultComboBoxModel<Object> model2 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());
      model2.insertElementAt("全て", 0);
      cbKenshuusha.setModel(model2);
   }

   private void setupTable() {
      becchuuTable.setRowHeight(25);
      BecchuuKanriFormTableModel model = new BecchuuKanriFormTableModel(becchuuRepository);
      becchuuTable.setModel(model);
      becchuuTable.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            System.out.println(e.getClickCount());
            if (e.getClickCount() == 2) {
               becchuuTableDoubleClicked(e);
            } else if (e.getClickCount() == 1) {
               becchuuTableSingleClicked(e);
            }
         }
      });

//      rowSorter = new TableRowSorter<TableModel>(becchuuTable.getModel());
//      becchuuTable.setRowSorter(rowSorter);

   }

   private void initializeData() {
      becchuuRepository = new BecchuuRepository();
   }

   private void regisEventHandler() {
      becchuuTable.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseMoved(MouseEvent e) {
            Point point = e.getPoint();
            if (becchuuTable.columnAtPoint(point) == becchuuTable.getColumnCount() - 1) {
               becchuuTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
               becchuuTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
         }
      });
      txtBecchuuSearch.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            searchTextBoxKeyReleased(e);
         }
      });

      btnReset.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            txtBecchuuSearch.setText("");
            txtSakuseiBI.setText("");
            cbSakuseiSha.setSelectedIndex(0);
            cbKenshuusha.setSelectedIndex(0);
            cbBecchuuType.setSelectedIndex(0);
            chkSakuseiZumi.setSelected(true);
            chkKenshuZumi.setSelected(true);
            textFilter = null;
            sakuseiBiFilter = null;
            becchuuTable.setRowFilter(null);
         }
      });
      ActionListener comboBoxActionListener = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            comboboxActionListner(e);
         }
      };
      cbSakuseiSha.setSelectedIndex(0);
      cbKenshuusha.setSelectedIndex(0);
      cbBecchuuType.setSelectedIndex(0);

      cbSakuseiSha.addActionListener(comboBoxActionListener);
      cbKenshuusha.addActionListener(comboBoxActionListener);
      cbBecchuuType.addActionListener(comboBoxActionListener);
      btnRefresh.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            ((BecchuuKanriFormTableModel) becchuuTable.getModel()).refresh();
         }
      });

      ActionListener checkBoxActionLister = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            checkBoxActionPerformed(e);
         }
      };
      chkSakuseiZumi.addActionListener(checkBoxActionLister);
      chkKenshuZumi.addActionListener(checkBoxActionLister);
   }

   // BEGIN: handle Events;
   private void becchuuTableDoubleClicked(MouseEvent event) {
      if (becchuuTable.getSelectedRow() == -1) return;
      int row = becchuuTable.getSelectedRow();
      String koujibangou = becchuuTable.getValueAt(row, 2).toString();
      String becchuuKigou = becchuuTable.getValueAt(row, 0).toString();
      Becchuu becchuuToEdit = becchuuRepository.getBecchuuByID(koujibangou, becchuuKigou);

      if (becchuuToEdit != null) {
         if (becchuuDetailsForm == null) {
            becchuuDetailsForm = new BecchuuDetailsForm(becchuuToEdit);
            becchuuDetailsForm.setLocationRelativeTo(this);
            becchuuDetailsForm.delegate = this;
            becchuuDetailsForm.showDialog(this);
            becchuuDetailsForm.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosed(WindowEvent e) {
                  becchuuDetailsForm = null;
                  super.windowClosed(e);
               }
            });
         } else {
            becchuuDetailsForm.setBecchuuToEdit(becchuuToEdit);
            java.awt.EventQueue.invokeLater(new Runnable() {
               @Override
               public void run() {
                  becchuuDetailsForm.toFront();
                  becchuuDetailsForm.repaint();
               }
            });
         }

      }
   }

   private void becchuuTableSingleClicked(MouseEvent event) {
      if (becchuuTable.getSelectedRow() == -1) return;

      int col = becchuuTable.getSelectedColumn();
      if (col != becchuuTable.getColumnCount() - 1) return;
      int row = becchuuTable.getSelectedRow();
      String koujibangou = becchuuTable.getValueAt(row, 2).toString();
      String becchuuKigou = becchuuTable.getValueAt(row, 0).toString();
      Becchuu becchuu = becchuuRepository.getBecchuuByID(koujibangou, becchuuKigou);

      if (becchuu.getHinCode() == null) {
         return;
      }

      String link = becchuu.getBecchuuDBURL();
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

   // BEGIN: search on becchuu
   LinkedList<RowFilter<TableModel, Object>> rowFilters = new LinkedList<>();
   RowFilter textFilter;
   RowFilter sakuseiShaFilter;
   RowFilter kenshuShaFilter;
   RowFilter becchuuTypeFilter;
   RowFilter sakuseiStatusFilter;
   RowFilter kenshuuStatusFilter;
   RowFilter sakuseiBiFilter;

   private void filterBecchuuTable() {
      rowFilters.clear();
      if (textFilter != null) {
         rowFilters.add(textFilter);
      }
      if (sakuseiShaFilter != null) {
         rowFilters.add(sakuseiShaFilter);
      }
      if (kenshuShaFilter != null) {
         rowFilters.add(kenshuShaFilter);
      }
      if (becchuuTypeFilter != null) {
         rowFilters.add(becchuuTypeFilter);
      }
      if (sakuseiStatusFilter != null) {
         rowFilters.add(sakuseiStatusFilter);
      }
      if (kenshuuStatusFilter != null) {
         rowFilters.add(kenshuuStatusFilter);
      }
      if (sakuseiBiFilter != null)
         rowFilters.add(sakuseiBiFilter);
      becchuuTable.setRowFilter(RowFilter.andFilter(rowFilters));
      // remake status label
      int maisu = 0;
      int misu = 0;
      for (int i = 0; i < becchuuTable.getRowCount(); i++) {
         try {
            maisu += Integer.valueOf(becchuuTable.getValueAt(i, 6).toString());
            misu += Integer.valueOf(becchuuTable.getValueAt(i, 8).toString());
         } catch (Exception e) {
            continue;
         }
      }
      float percentage = (float) (maisu - misu) * 100 / maisu;
      lblStatus.setText("別注枚数：" + maisu + " - " + "ミス：" + misu + "良質：" + percentage + "％");
   }

   private void searchTextBoxKeyReleased(KeyEvent e) {

      if (!(e.getSource() instanceof JTextField)) return;
      JTextField source = (JTextField) e.getSource();
      if (source == txtBecchuuSearch) {
         if (source.getText().trim().length() == 0) {
            textFilter = null;
         } else {
            String regex = Helpers.convertGlobToRegExCaseInsensitive(source.getText());
            textFilter = RowFilter.regexFilter(regex, 0, 2, 3, 4, becchuuTable.getColumnCount() - 1);
         }
      } else if (source == txtSakuseiBI) {
         if (source.getText().trim().length() == 0) {
            sakuseiBiFilter = null;
         } else {
            String regex = Helpers.convertGlobToRegExCaseInsensitive(source.getText());
            sakuseiBiFilter = RowFilter.regexFilter(regex, becchuuTable.getColumnCount() - 2);
         }
      }
      filterBecchuuTable();
   }

   private void checkBoxActionPerformed(ActionEvent event) {
      if (!(event.getSource() instanceof JCheckBox)) return;
      JCheckBox source = (JCheckBox) event.getSource();
      String shoriZumi = "^(?!処理済み).*";
      if (source == chkSakuseiZumi) {
         sakuseiStatusFilter = source.isSelected() ? null : RowFilter.regexFilter(shoriZumi, 7);
      } else if (source == chkKenshuZumi) {
         if (source.isSelected()) {
            chkSakuseiZumi.setSelected(true);
            event.setSource(chkSakuseiZumi);
            checkBoxActionPerformed(event);
         }

         kenshuuStatusFilter = source.isSelected() ? null : RowFilter.regexFilter(shoriZumi, 10);
      }
      filterBecchuuTable();
   }

   private void comboboxActionListner(ActionEvent event) {

      Object source = event.getSource();
      if (source == cbSakuseiSha) {
         if (cbSakuseiSha.getSelectedIndex() == 0) {
            sakuseiShaFilter = null;
         } else {
            sakuseiShaFilter = RowFilter.regexFilter(cbSakuseiSha.getModel().getSelectedItem().toString(), 5);
         }
      } else if (source == cbKenshuusha) {
         if (cbKenshuusha.getSelectedIndex() == 0) {
            kenshuShaFilter = null;
         } else {
            kenshuShaFilter = RowFilter.regexFilter(cbKenshuusha.getModel().getSelectedItem().toString(), 9);
         }
      } else if (source == cbBecchuuType) {
         becchuuTypeFilter = cbBecchuuType.getSelectedIndex() == 0 ? null :
             RowFilter.regexFilter(cbBecchuuType.getModel().getSelectedItem().toString(), 1);
      }

      filterBecchuuTable();
   }

   // END: search on becchuu


   // END: handle Events
   private void createAndSetUpGUI() {
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);
      String[] columnNames1 = {"Title", "User Name", "Password", "URL", "Notes"};
      contentPane.setLayout(new BorderLayout(0, 0));

      JPanel headerPanel = new JPanel();
      FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
      flowLayout.setAlignment(FlowLayout.LEFT);
      headerPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
      contentPane.add(headerPanel, BorderLayout.NORTH);

      JLabel lblNewLabel_3 = new JLabel("別注管理システム");
      lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 15));
      headerPanel.add(lblNewLabel_3);

      JPanel maincontent = new JPanel();
      maincontent.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
      contentPane.add(maincontent, BorderLayout.CENTER);
      maincontent.setLayout(new BorderLayout(0, 0));

      JPanel becchuuSearchPanel = new JPanel();
      maincontent.add(becchuuSearchPanel, BorderLayout.NORTH);
      GridBagLayout gbl_becchuuSearchPanel = new GridBagLayout();
      gbl_becchuuSearchPanel.columnWidths = new int[]{0, 103, 42, 28, 42, 28, 30, 28, 97, 73, 91, 0, 0};
      gbl_becchuuSearchPanel.rowHeights = new int[]{21, 0};
      gbl_becchuuSearchPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
      gbl_becchuuSearchPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
      becchuuSearchPanel.setLayout(gbl_becchuuSearchPanel);

      btnReset = new JButton("");
      btnReset.setOpaque(false);
      btnReset.setBorderPainted(false);
      btnReset.setContentAreaFilled(false);
      btnReset.setIcon(new ImageIcon(BecchuuKanriForm.class.getResource("/resources/icon/icon-reset.png")));
      GridBagConstraints gbc_btnReset = new GridBagConstraints();
      gbc_btnReset.insets = new Insets(0, 0, 0, 5);
      gbc_btnReset.gridx = 0;
      gbc_btnReset.gridy = 0;
      becchuuSearchPanel.add(btnReset, gbc_btnReset);

      txtBecchuuSearch = new JTextField();

      GridBagConstraints gbc_txtBecchuuSearch = new GridBagConstraints();
      gbc_txtBecchuuSearch.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtBecchuuSearch.weightx = 1.0;
      gbc_txtBecchuuSearch.insets = new Insets(0, 0, 0, 5);
      gbc_txtBecchuuSearch.gridx = 1;
      gbc_txtBecchuuSearch.gridy = 0;
      becchuuSearchPanel.add(txtBecchuuSearch, gbc_txtBecchuuSearch);
      txtBecchuuSearch.setColumns(10);

      JLabel lblNewLabel = new JLabel("作成者");
      GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
      gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
      gbc_lblNewLabel.gridx = 2;
      gbc_lblNewLabel.gridy = 0;
      becchuuSearchPanel.add(lblNewLabel, gbc_lblNewLabel);


      cbSakuseiSha = new JComboBox();

      GridBagConstraints gbc_cbSakuseiSha = new GridBagConstraints();
      gbc_cbSakuseiSha.anchor = GridBagConstraints.WEST;
      gbc_cbSakuseiSha.insets = new Insets(0, 0, 0, 5);
      gbc_cbSakuseiSha.gridx = 3;
      gbc_cbSakuseiSha.gridy = 0;
      becchuuSearchPanel.add(cbSakuseiSha, gbc_cbSakuseiSha);

      JLabel lblNewLabel_1 = new JLabel("研修者");
      GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
      gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
      gbc_lblNewLabel_1.gridx = 4;
      gbc_lblNewLabel_1.gridy = 0;
      becchuuSearchPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

      cbKenshuusha = new JComboBox();

      GridBagConstraints gbc_cbKenshuusha = new GridBagConstraints();
      gbc_cbKenshuusha.anchor = GridBagConstraints.WEST;
      gbc_cbKenshuusha.insets = new Insets(0, 0, 0, 5);
      gbc_cbKenshuusha.gridx = 5;
      gbc_cbKenshuusha.gridy = 0;
      becchuuSearchPanel.add(cbKenshuusha, gbc_cbKenshuusha);

      JLabel lblNewLabel_2 = new JLabel("分類");
      GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
      gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
      gbc_lblNewLabel_2.gridx = 6;
      gbc_lblNewLabel_2.gridy = 0;
      becchuuSearchPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

      cbBecchuuType = new JComboBox();

      GridBagConstraints gbc_cbBecchuuType = new GridBagConstraints();
      gbc_cbBecchuuType.anchor = GridBagConstraints.WEST;
      gbc_cbBecchuuType.insets = new Insets(0, 0, 0, 5);
      gbc_cbBecchuuType.gridx = 7;
      gbc_cbBecchuuType.gridy = 0;
      becchuuSearchPanel.add(cbBecchuuType, gbc_cbBecchuuType);

      chkSakuseiZumi = new JCheckBox("作成済み表示");
      chkSakuseiZumi.setSelected(true);

      GridBagConstraints gbc_chkSakuseiZumi = new GridBagConstraints();
      gbc_chkSakuseiZumi.anchor = GridBagConstraints.WEST;
      gbc_chkSakuseiZumi.insets = new Insets(0, 0, 0, 5);
      gbc_chkSakuseiZumi.gridx = 8;
      gbc_chkSakuseiZumi.gridy = 0;
      becchuuSearchPanel.add(chkSakuseiZumi, gbc_chkSakuseiZumi);

      chkKenshuZumi = new JCheckBox("検収済み表示");
      chkKenshuZumi.setSelected(true);
      GridBagConstraints gbc_chkKenshuZumi = new GridBagConstraints();
      gbc_chkKenshuZumi.anchor = GridBagConstraints.WEST;
      gbc_chkKenshuZumi.insets = new Insets(0, 0, 0, 5);
      gbc_chkKenshuZumi.gridx = 9;
      gbc_chkKenshuZumi.gridy = 0;
      becchuuSearchPanel.add(chkKenshuZumi, gbc_chkKenshuZumi);

      lblNewLabel_4 = new JLabel("作成日");
      GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
      gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
      gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel_4.gridx = 10;
      gbc_lblNewLabel_4.gridy = 0;
      becchuuSearchPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);

      txtSakuseiBI = new JTextField();
      txtSakuseiBI.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            searchTextBoxKeyReleased(e);
         }
      });
      GridBagConstraints gbc_txtSakuseiBI = new GridBagConstraints();
      gbc_txtSakuseiBI.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtSakuseiBI.gridx = 11;
      gbc_txtSakuseiBI.gridy = 0;
      becchuuSearchPanel.add(txtSakuseiBI, gbc_txtSakuseiBI);
      txtSakuseiBI.setColumns(10);


      becchuuTable = new JXTable();
      becchuuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      becchuuTable.setModel(new DefaultTableModel(null, columnNames1));

      JScrollPane scrollPane_1 = new JScrollPane();
      maincontent.add(scrollPane_1, BorderLayout.CENTER);
      scrollPane_1.setViewportView(becchuuTable);

      JPanel footerButtonPanel = new JPanel();
      maincontent.add(footerButtonPanel, BorderLayout.SOUTH);
      footerButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));

      btnRefresh = new JButton("");
      btnRefresh.setOpaque(false);
      btnRefresh.setBorderPainted(false);
      btnRefresh.setContentAreaFilled(false);
      btnRefresh.setIcon(new ImageIcon(BecchuuKanriForm.class.getResource("/resources/icon/icon-refresh.png")));
      footerButtonPanel.add(btnRefresh);

      JPanel statusPanel = new JPanel();
      statusPanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
      FlowLayout flowLayout_1 = (FlowLayout) statusPanel.getLayout();
      flowLayout_1.setAlignment(FlowLayout.LEFT);
      contentPane.add(statusPanel, BorderLayout.SOUTH);

      lblStatus = new JLabel("別注枚数：1000 - ミス");
      statusPanel.add(lblStatus);
   }

   private JPanel contentPane;
   private JXTable becchuuTable;
   private JLabel lblWelcome;
   private JComboBox cbType;
   private JComboBox cbSakuseiSha;
   private JComboBox cbKenshuusha;
   private JComboBox cbBecchuuType;
   private JCheckBox chkSakuseiZumi;
   private JCheckBox chkKenshuZumi;
   private JTextField txtBecchuuSearch;
   private JButton btnRefresh;
   private JButton btnReset;
   private JLabel lblStatus;
   private JTextField txtHinCode;
   private JTextField txtSakuseiBI;
   private JLabel lblNewLabel_4;


   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      Config.setLookAndField();
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               BecchuuKanriForm frame = new BecchuuKanriForm();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }


   @Override
   public void submitData(BecchuuDetailsForm becchuuDetailsForm, Becchuu becchuu) {
      try {
         BecchuuKanriFormTableModel becchuuKanriFormTableModel = (BecchuuKanriFormTableModel) becchuuTable.getModel();
         if (becchuuKanriFormTableModel.updateBecchuu(becchuu, becchuuTable.getSelectedRow()) != null) {
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   @Override
   public void movePrevious(BecchuuDetailsForm becchuuDetailsForm) {
      int row = becchuuTable.getSelectedRow() - 1;
      if (row < 0) return;
      String koujibangou = becchuuTable.getValueAt(row, 2).toString();
      String becchuuKigou = becchuuTable.getValueAt(row, 0).toString();
      Becchuu becchuuToEdit = becchuuRepository.getBecchuuByID(koujibangou, becchuuKigou);

      if (becchuuToEdit != null) {
         becchuuDetailsForm.setBecchuuToEdit(becchuuToEdit);
         becchuuTable.addRowSelectionInterval(row, row);
      }
   }

   @Override
   public void moveNext(BecchuuDetailsForm becchuuDetailsForm) {
      int row = becchuuTable.getSelectedRow() + 1;
      if (row > becchuuTable.getRowCount() - 1) return;
      String koujibangou = becchuuTable.getValueAt(row, 2).toString();
      String becchuuKigou = becchuuTable.getValueAt(row, 0).toString();
      Becchuu becchuuToEdit = becchuuRepository.getBecchuuByID(koujibangou, becchuuKigou);

      if (becchuuToEdit != null) {
         becchuuDetailsForm.setBecchuuToEdit(becchuuToEdit);
         becchuuTable.addRowSelectionInterval(row, row);
      }
   }
}
