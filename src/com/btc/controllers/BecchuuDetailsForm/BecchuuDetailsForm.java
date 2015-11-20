package com.btc.controllers.BecchuuDetailsForm;

import com.btc.controllers.DialogHelpers;
import com.btc.model.*;
import com.btc.repositoty.CommonRepository;
import com.btc.supports.DateLabelFormatter;
import com.btc.supports.Helpers;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class BecchuuDetailsForm extends JDialog {

   private JPanel contentPane;
   private JLabel lblNewLabel;
   private JTextField txtMotozuKigou;
   private JDatePickerImpl dpkSakuseBi;

   public BecchuuDetailsDelegate delegate;
   private JTextField txtBecchuuKigou;
   private JTextArea txtBecchuuParameter;
   private JTextArea txtBecchuuNaiyou;
   private JTextArea txtMotozuParameter;

   private Becchuu becchuuToEdit;
   private JLabel txtIraisha;
   private JLabel ltxtIraiBi;
   private JLabel txtNouki;
   private JTextField txtBukkenMei;
   private JTextField txtShiten;
   private JLabel txtShouhinType;
   private JTextField txtDEPSF;
   private JComboBox cbSakuseiJoukyou;
   private JSpinner spMaisuu;
   private JComboBox cbKenshuuSha;
   private JComboBox cbKenshuuJoukyou;
   private JSpinner spMisu;
   private JTextArea txtBikou;
   private JComboBox cbsakuSeiSha;
   private JComboBox cbBunrui;
   private JTextField txtHinCode;
   private JTextField txtKoujibangou;
   private JButton btnLock;

   private boolean editable = false;
   private void loadBecchuuEmployeesCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());
      model.addElement("");
      cbsakuSeiSha.setModel(model);

      DefaultComboBoxModel<Object> model2 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuHandEmployees().toArray());
      model2.addElement("");
      cbKenshuuSha.setModel(model2);

   }

   private void loadBecchuuStatusCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuStatus());
      cbSakuseiJoukyou.setModel(model);

      DefaultComboBoxModel<Object> model1 = new DefaultComboBoxModel<>(CommonRepository.getBecchuuStatus());
      cbKenshuuJoukyou.setModel(model1);
   }

   private void loadBunruiCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(CommonRepository.getBecchuuTypes().toArray());
      cbBunrui.setModel(model);
   }
   
   private void setField(JComponent component, String value) {
	   if (component instanceof JTextArea) {
		   JTextArea textArea = (JTextArea)component;
		   try {
			   textArea.setText(value);
		   } catch (Exception exception) {
			   textArea.setText("");
		   }
	   } else if (component instanceof JTextField) {
		   JTextField textArea = (JTextField)component;
		   try {
			   textArea.setText(value);
		   } catch (Exception exception) {
			   textArea.setText("");
		   }
	   } else if (component instanceof JLabel) {
		   JLabel textArea = (JLabel)component;
		   try {
			   textArea.setText(value);
		   } catch (Exception exception) {
			   textArea.setText("");
		   }
	   }
   }
   
   private void setCombobox(JComboBox comboBox, Object value) {
	   try {
		   comboBox.getModel().setSelectedItem(value);
	   } catch (Exception exception) {
		   comboBox.getModel().setSelectedItem(comboBox.getModel().getSize() - 1);
	   }
   }

   private void populateData() {
	  
	   setField(txtBecchuuKigou, becchuuToEdit.getBecchuuKigou());
	   setField(txtBecchuuParameter, becchuuToEdit.getBecchuuParameter());
	   setField(txtBecchuuNaiyou, becchuuToEdit.getBecchuuNaiyou());
	  
	   setField(txtMotozuKigou, becchuuToEdit.getMotozuKigou());
	   setField(txtMotozuParameter, becchuuToEdit.getMotozuParameter());
      
	   if (becchuuToEdit.getIraiSha() != null)
		   setField(txtIraisha, becchuuToEdit.getIraiSha().getName());
	   
	   setField(ltxtIraiBi, Helpers.stringFromDate(becchuuToEdit.getIraibi()));
      setField(txtKoujibangou, becchuuToEdit.getKoujibangou());	   
     
      Bukken bukken = becchuuToEdit.getBukken();
      setField(txtNouki, Helpers.stringFromDate(bukken.getNouki()));

      String bukkenMei = bukken.getName();
      if (bukken.getYobikata() != null) {
         bukkenMei += "（" + bukken.getYobikata() + "）";
      }
      setField(txtBukkenMei, bukkenMei);
      setField(txtShiten, bukken.getShiten());
      setField(txtShouhinType, bukken.getBukkenType());
      setField(txtDEPSF, bukken.getDepsf());

      Calendar calendar = Calendar.getInstance();
      Date sakuseiBi = becchuuToEdit.getSakuseiBi();
      if (sakuseiBi == null) {
         sakuseiBi = new Date();
      }
      calendar.setTime(sakuseiBi);
      dpkSakuseBi.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DATE));
      dpkSakuseBi.getModel().setSelected(true);

      
      Employee sakuseiSha = becchuuToEdit.getSakuseiSha();
      setCombobox(cbsakuSeiSha, sakuseiSha);
      
      Employee kenshuuSha = becchuuToEdit.getKenshuuSha();
      setCombobox(cbKenshuuSha, kenshuuSha);

      BecchuuStatus sakuseiJoukyou = becchuuToEdit.getSakuseiStatus();
      setCombobox(cbSakuseiJoukyou, sakuseiJoukyou);

      BecchuuStatus kenshuuJoukyou = becchuuToEdit.getKenshuuStatus();
      setCombobox(cbKenshuuJoukyou, kenshuuJoukyou);
//		
      int misu = becchuuToEdit.getMisu();
      spMisu.getModel().setValue(misu < 0 ? 0 : misu);
      int maisu = becchuuToEdit.getBecchuuMaisu();
      spMaisuu.getModel().setValue(maisu < 0 ? 0 : maisu);

      BecchuuType becchuuType = becchuuToEdit.getBecchuuType();
      setCombobox(cbBunrui, becchuuType);

      setField(txtHinCode, becchuuToEdit.getHinCode());
      setField(txtBikou, becchuuToEdit.getBikou());

      txtBikou.requestFocus();
      setFormEditable(false);
   }

   // BEGIN handle Events

   private void btnOKActionPerformed(ActionEvent event) {
      becchuuToEdit.setBecchuuKigou(txtBecchuuKigou.getText().trim());
      becchuuToEdit.setBecchuuParameter(txtBecchuuParameter.getText().trim());
      becchuuToEdit.setBecchuuNaiyou(txtBecchuuNaiyou.getText().trim());
      becchuuToEdit.setMotozuKigou(txtMotozuKigou.getText().trim());
      becchuuToEdit.setMotozuParameter(txtMotozuParameter.getText().trim());

      Employee sakuseiSha = (Employee) cbsakuSeiSha.getModel().getSelectedItem();
      if (sakuseiSha != null)
         becchuuToEdit.setSakuseiShaID(sakuseiSha.getId());

      Employee kenshuuSha = (Employee) cbKenshuuSha.getModel().getSelectedItem();
      if (kenshuuSha != null)
         becchuuToEdit.setKenshuShaID(kenshuuSha.getId());

      BecchuuStatus sakuseiStatus = (BecchuuStatus)cbSakuseiJoukyou.getModel().getSelectedItem();
      if (sakuseiStatus != null)
         becchuuToEdit.setSakuseiStatusID(sakuseiStatus.getId());

      BecchuuStatus kenshuuStatus = (BecchuuStatus)cbKenshuuJoukyou.getModel().getSelectedItem();
      if (kenshuuStatus != null)
         becchuuToEdit.setKenshuuStatusID(kenshuuStatus.getId());

      BecchuuType becchuuType = (BecchuuType) cbBunrui.getModel().getSelectedItem();
      if (becchuuType != null)
         becchuuToEdit.setBecchuuTypeID(becchuuType.getId());

      becchuuToEdit.setSakuseiBi(((Date) dpkSakuseBi.getModel().getValue()));
      becchuuToEdit.setBecchuuMaisu(Integer.parseInt(spMaisuu.getModel().getValue().toString()));
      becchuuToEdit.setMisu(Integer.parseInt(spMisu.getModel().getValue().toString()));
      becchuuToEdit.setHinCode(txtHinCode.getText().trim());
      becchuuToEdit.setBikou(txtBikou.getText().trim());

      // 更新実行
      this.delegate.submitData(this, becchuuToEdit);
   }

   // END handle Events

   private void createAndSetupGUI() {
      contentPane = new JPanel();
      contentPane.setBorder(null);
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);

      Border margin = BorderFactory.createEmptyBorder(0, 5, 0, 5);
      Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);

      JPanel headerPanel = new JPanel();
      headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
      FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
      flowLayout.setAlignment(FlowLayout.LEADING);
      headerPanel.setBackground(Color.lightGray);
      contentPane.add(headerPanel, BorderLayout.NORTH);

      lblNewLabel = new JLabel("別注詳細");
      lblNewLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
      lblNewLabel.setForeground(new Color(0, 0, 0));
      lblNewLabel.setBackground(new Color(255, 255, 255));
      headerPanel.add(lblNewLabel);

      Properties p = new Properties();
      p.put("text.today", "Today");
      p.put("text.month", "Month");
      p.put("text.year", "Year");
      UtilDateModel model = new UtilDateModel();
      JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

      JPanel maincontentPanel = new JPanel();
      contentPane.add(maincontentPanel, BorderLayout.CENTER);
      maincontentPanel.setLayout(new GridLayout(0, 2, 0, 0));

      JPanel becchuuPanel = new JPanel();
      maincontentPanel.add(becchuuPanel);
      becchuuPanel.setLayout(new BorderLayout(0, 0));

      JPanel becchuuHeader = new JPanel();

      becchuuHeader.setBorder(new CompoundBorder(margin, bottomLine));
      FlowLayout fl_becchuuHeader = (FlowLayout) becchuuHeader.getLayout();
      fl_becchuuHeader.setAlignment(FlowLayout.LEFT);
      becchuuPanel.add(becchuuHeader, BorderLayout.NORTH);

      JLabel label_1 = new JLabel("別注情報");
      label_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
      becchuuHeader.add(label_1);

      JPanel becchuuContentPanel = new JPanel();
      becchuuPanel.add(becchuuContentPanel);
      becchuuContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      GridBagLayout gbl_becchuuContentPanel = new GridBagLayout();
      gbl_becchuuContentPanel.columnWidths = new int[]{0, 0, 0};
      gbl_becchuuContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
      gbl_becchuuContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
      gbl_becchuuContentPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
      becchuuContentPanel.setLayout(gbl_becchuuContentPanel);

      JLabel lblNewLabel_1 = new JLabel("別注記号：");
      GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
      gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_1.gridx = 0;
      gbc_lblNewLabel_1.gridy = 0;
      becchuuContentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

      txtBecchuuKigou = new JTextField();
      GridBagConstraints gbc_txtBecchuuKigou = new GridBagConstraints();
      gbc_txtBecchuuKigou.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtBecchuuKigou.insets = new Insets(0, 0, 5, 0);
      gbc_txtBecchuuKigou.gridx = 1;
      gbc_txtBecchuuKigou.gridy = 0;
      becchuuContentPanel.add(txtBecchuuKigou, gbc_txtBecchuuKigou);
      txtBecchuuKigou.setColumns(10);

      JLabel lblNewLabel_2 = new JLabel("別注パラメータ：");
      GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
      gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_2.gridx = 0;
      gbc_lblNewLabel_2.gridy = 1;
      becchuuContentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      GridBagConstraints gbc_scrollPane = new GridBagConstraints();
      gbc_scrollPane.fill = GridBagConstraints.BOTH;
      gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
      gbc_scrollPane.gridx = 1;
      gbc_scrollPane.gridy = 1;
      becchuuContentPanel.add(scrollPane, gbc_scrollPane);

      txtBecchuuParameter = new JTextArea();
      scrollPane.setViewportView(txtBecchuuParameter);
      scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtBecchuuParameter.setLineWrap(true);
      txtBecchuuParameter.setRows(5);

      JLabel lblUserName = new JLabel("別注内容：");
      GridBagConstraints gbc_lblUserName = new GridBagConstraints();
      gbc_lblUserName.anchor = GridBagConstraints.WEST;
      gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
      gbc_lblUserName.gridx = 0;
      gbc_lblUserName.gridy = 2;
      becchuuContentPanel.add(lblUserName, gbc_lblUserName);

      JScrollPane scrollPane_1 = new JScrollPane();
      scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
      gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
      gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
      gbc_scrollPane_1.gridx = 1;
      gbc_scrollPane_1.gridy = 2;
      becchuuContentPanel.add(scrollPane_1, gbc_scrollPane_1);

      txtBecchuuNaiyou = new JTextArea();
      scrollPane_1.setViewportView(txtBecchuuNaiyou);
      scrollPane_1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtBecchuuNaiyou.setRows(5);

      JLabel lblPassword = new JLabel("元図記号：");
      GridBagConstraints gbc_lblPassword = new GridBagConstraints();
      gbc_lblPassword.anchor = GridBagConstraints.WEST;
      gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
      gbc_lblPassword.gridx = 0;
      gbc_lblPassword.gridy = 3;
      becchuuContentPanel.add(lblPassword, gbc_lblPassword);

      txtMotozuKigou = new JTextField();
      GridBagConstraints gbc_txtMotozuKigou = new GridBagConstraints();
      gbc_txtMotozuKigou.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtMotozuKigou.anchor = GridBagConstraints.NORTH;
      gbc_txtMotozuKigou.insets = new Insets(0, 0, 5, 0);
      gbc_txtMotozuKigou.gridx = 1;
      gbc_txtMotozuKigou.gridy = 3;
      becchuuContentPanel.add(txtMotozuKigou, gbc_txtMotozuKigou);
      txtMotozuKigou.setColumns(10);

      JLabel lblRepeat = new JLabel("元図パラメータ：");
      GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
      gbc_lblRepeat.anchor = GridBagConstraints.WEST;
      gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
      gbc_lblRepeat.gridx = 0;
      gbc_lblRepeat.gridy = 4;
      becchuuContentPanel.add(lblRepeat, gbc_lblRepeat);

      JScrollPane scrollPane_2 = new JScrollPane();
      scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
      gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
      gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
      gbc_scrollPane_2.gridx = 1;
      gbc_scrollPane_2.gridy = 4;
      becchuuContentPanel.add(scrollPane_2, gbc_scrollPane_2);

      txtMotozuParameter = new JTextArea();
      txtMotozuParameter.setLineWrap(true);
      scrollPane_2.setViewportView(txtMotozuParameter);
      scrollPane_2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtMotozuParameter.setRows(5);

      JLabel lblNewLabel_4 = new JLabel("依頼者：");
      GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
      gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_4.gridx = 0;
      gbc_lblNewLabel_4.gridy = 5;
      becchuuContentPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);

      txtIraisha = new JLabel("New label");
      GridBagConstraints gbc_txtIraisha = new GridBagConstraints();
      gbc_txtIraisha.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtIraisha.insets = new Insets(0, 0, 5, 0);
      gbc_txtIraisha.gridx = 1;
      gbc_txtIraisha.gridy = 5;
      becchuuContentPanel.add(txtIraisha, gbc_txtIraisha);

      JLabel label = new JLabel("依頼日：");
      GridBagConstraints gbc_label = new GridBagConstraints();
      gbc_label.anchor = GridBagConstraints.WEST;
      gbc_label.insets = new Insets(0, 0, 0, 5);
      gbc_label.gridx = 0;
      gbc_label.gridy = 6;
      becchuuContentPanel.add(label, gbc_label);

      ltxtIraiBi = new JLabel("New label");
      GridBagConstraints gbc_ltxtIraiBi = new GridBagConstraints();
      gbc_ltxtIraiBi.fill = GridBagConstraints.HORIZONTAL;
      gbc_ltxtIraiBi.gridx = 1;
      gbc_ltxtIraiBi.gridy = 6;
      becchuuContentPanel.add(ltxtIraiBi, gbc_ltxtIraiBi);

      JPanel kanriPanel = new JPanel();
      maincontentPanel.add(kanriPanel);
      kanriPanel.setLayout(new BorderLayout(0, 0));

      JPanel bukkenHeader = new JPanel();
      bukkenHeader.setBorder(new CompoundBorder(margin, bottomLine));

      FlowLayout fl_bukkenHeader = (FlowLayout) bukkenHeader.getLayout();
      fl_bukkenHeader.setAlignment(FlowLayout.LEFT);
      kanriPanel.add(bukkenHeader, BorderLayout.NORTH);

      JLabel label_2 = new JLabel("管理情報");
      label_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
      bukkenHeader.add(label_2);


      JPanel bukkenPanel = new JPanel();
      bukkenPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      kanriPanel.add(bukkenPanel);
      GridBagLayout gbl_bukkenPanel = new GridBagLayout();
      gbl_bukkenPanel.columnWidths = new int[]{0, 0, 0};
      gbl_bukkenPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      gbl_bukkenPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
      gbl_bukkenPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
      bukkenPanel.setLayout(gbl_bukkenPanel);

      JLabel lblNewLabel_3 = new JLabel("納期：");
      GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
      gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_3.gridx = 0;
      gbc_lblNewLabel_3.gridy = 0;
      bukkenPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);

      txtNouki = new JLabel("New label");
      GridBagConstraints gbc_txtNouki = new GridBagConstraints();
      gbc_txtNouki.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtNouki.anchor = GridBagConstraints.SOUTH;
      gbc_txtNouki.insets = new Insets(0, 0, 5, 0);
      gbc_txtNouki.gridx = 1;
      gbc_txtNouki.gridy = 0;
      bukkenPanel.add(txtNouki, gbc_txtNouki);

      JLabel lblNewLabel_5 = new JLabel("工事番号：");
      GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
      gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_5.gridx = 0;
      gbc_lblNewLabel_5.gridy = 1;
      bukkenPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
      
      txtKoujibangou = new JTextField();
      txtKoujibangou.setBackground(new Color(0, 0, 0, 0));
      txtKoujibangou.setBorder(null);
      txtKoujibangou.setEditable(false);
      GridBagConstraints gbc_txtKoujibangou = new GridBagConstraints();
      gbc_txtKoujibangou.insets = new Insets(0, 0, 5, 0);
      gbc_txtKoujibangou.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtKoujibangou.gridx = 1;
      gbc_txtKoujibangou.gridy = 1;
      bukkenPanel.add(txtKoujibangou, gbc_txtKoujibangou);
      txtKoujibangou.setColumns(10);

      JLabel lblNewLabel_6 = new JLabel("施主名：");
      GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
      gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_6.gridx = 0;
      gbc_lblNewLabel_6.gridy = 2;
      bukkenPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);

      txtBukkenMei = new JTextField("New label");
      txtBukkenMei.setBackground(new Color(0, 0, 0, 0));
      txtBukkenMei.setBorder(null);
      txtBukkenMei.setEditable(false);
      GridBagConstraints gbc_txtBukkenMei = new GridBagConstraints();
      gbc_txtBukkenMei.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtBukkenMei.insets = new Insets(0, 0, 5, 0);
      gbc_txtBukkenMei.gridx = 1;
      gbc_txtBukkenMei.gridy = 2;
      bukkenPanel.add(txtBukkenMei, gbc_txtBukkenMei);

      JLabel lblNewLabel_7 = new JLabel("支店：");
      GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
      gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_7.gridx = 0;
      gbc_lblNewLabel_7.gridy = 3;
      bukkenPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);

      txtShiten = new JTextField("New label");
      txtShiten.setBackground(new Color(0, 0, 0, 0));
      txtShiten.setBorder(null);
      txtShiten.setEditable(false);
      GridBagConstraints gbc_txtShiten = new GridBagConstraints();
      gbc_txtShiten.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtShiten.insets = new Insets(0, 0, 5, 0);
      gbc_txtShiten.gridx = 1;
      gbc_txtShiten.gridy = 3;
      bukkenPanel.add(txtShiten, gbc_txtShiten);

      JLabel lblNewLabel_8 = new JLabel("商品タイプ：");
      GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
      gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_8.gridx = 0;
      gbc_lblNewLabel_8.gridy = 4;
      bukkenPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);

      txtShouhinType = new JLabel("New label");
      GridBagConstraints gbc_txtShouhinType = new GridBagConstraints();
      gbc_txtShouhinType.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtShouhinType.insets = new Insets(0, 0, 5, 0);
      gbc_txtShouhinType.gridx = 1;
      gbc_txtShouhinType.gridy = 4;
      bukkenPanel.add(txtShouhinType, gbc_txtShouhinType);

      JLabel lblDepsf = new JLabel("DEPSF番号：");
      GridBagConstraints gbc_lblDepsf = new GridBagConstraints();
      gbc_lblDepsf.anchor = GridBagConstraints.WEST;
      gbc_lblDepsf.insets = new Insets(0, 0, 5, 5);
      gbc_lblDepsf.gridx = 0;
      gbc_lblDepsf.gridy = 5;
      bukkenPanel.add(lblDepsf, gbc_lblDepsf);

      txtDEPSF = new JTextField("New label");
      txtDEPSF.setBackground(new Color(0, 0, 0, 0));
      txtDEPSF.setBorder(null);
      txtDEPSF.setEditable(false);
      GridBagConstraints gbc_txtDEPSF = new GridBagConstraints();
      gbc_txtDEPSF.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtDEPSF.insets = new Insets(0, 0, 5, 0);
      gbc_txtDEPSF.gridx = 1;
      gbc_txtDEPSF.gridy = 5;
      bukkenPanel.add(txtDEPSF, gbc_txtDEPSF);

      JLabel lblNewLabel_9 = new JLabel("作成者：");
      GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
      gbc_lblNewLabel_9.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_9.gridx = 0;
      gbc_lblNewLabel_9.gridy = 6;
      bukkenPanel.add(lblNewLabel_9, gbc_lblNewLabel_9);

      cbsakuSeiSha = new JComboBox();
      GridBagConstraints gbc_cbsakuSeiSha = new GridBagConstraints();
      gbc_cbsakuSeiSha.insets = new Insets(0, 0, 5, 0);
      gbc_cbsakuSeiSha.fill = GridBagConstraints.HORIZONTAL;
      gbc_cbsakuSeiSha.gridx = 1;
      gbc_cbsakuSeiSha.gridy = 6;
      bukkenPanel.add(cbsakuSeiSha, gbc_cbsakuSeiSha);

      JLabel lblNewLabel_10 = new JLabel("作成状況：");
      GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
      gbc_lblNewLabel_10.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_10.gridx = 0;
      gbc_lblNewLabel_10.gridy = 7;
      bukkenPanel.add(lblNewLabel_10, gbc_lblNewLabel_10);

      cbSakuseiJoukyou = new JComboBox();
      GridBagConstraints gbc_cbSakuseiJoukyou = new GridBagConstraints();
      gbc_cbSakuseiJoukyou.fill = GridBagConstraints.HORIZONTAL;
      gbc_cbSakuseiJoukyou.insets = new Insets(0, 0, 5, 0);
      gbc_cbSakuseiJoukyou.gridx = 1;
      gbc_cbSakuseiJoukyou.gridy = 7;
      bukkenPanel.add(cbSakuseiJoukyou, gbc_cbSakuseiJoukyou);

      JLabel lblNewLabel_11 = new JLabel("作成日：");
      GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
      gbc_lblNewLabel_11.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_11.gridx = 0;
      gbc_lblNewLabel_11.gridy = 8;
      bukkenPanel.add(lblNewLabel_11, gbc_lblNewLabel_11);

      dpkSakuseBi = new JDatePickerImpl(datePanel, new DateLabelFormatter());
      GridBagConstraints gbc_dpkSakuseBi = new GridBagConstraints();
      gbc_dpkSakuseBi.fill = GridBagConstraints.HORIZONTAL;
      gbc_dpkSakuseBi.insets = new Insets(0, 0, 5, 0);
      gbc_dpkSakuseBi.gridx = 1;
      gbc_dpkSakuseBi.gridy = 8;
      bukkenPanel.add(dpkSakuseBi, gbc_dpkSakuseBi);

      JLabel lblNewLabel_14 = new JLabel("別注枚数：");
      GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
      gbc_lblNewLabel_14.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_14.gridx = 0;
      gbc_lblNewLabel_14.gridy = 9;
      bukkenPanel.add(lblNewLabel_14, gbc_lblNewLabel_14);

      spMaisuu = new JSpinner();
      spMaisuu.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
      GridBagConstraints gbc_spMaisuu = new GridBagConstraints();
      gbc_spMaisuu.fill = GridBagConstraints.HORIZONTAL;
      gbc_spMaisuu.insets = new Insets(0, 0, 5, 0);
      gbc_spMaisuu.gridx = 1;
      gbc_spMaisuu.gridy = 9;
      bukkenPanel.add(spMaisuu, gbc_spMaisuu);

      JLabel lblNewLabel_12 = new JLabel("研修者：");
      GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
      gbc_lblNewLabel_12.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_12.gridx = 0;
      gbc_lblNewLabel_12.gridy = 10;
      bukkenPanel.add(lblNewLabel_12, gbc_lblNewLabel_12);

      cbKenshuuSha = new JComboBox();
      GridBagConstraints gbc_cbKenshuuSha = new GridBagConstraints();
      gbc_cbKenshuuSha.fill = GridBagConstraints.HORIZONTAL;
      gbc_cbKenshuuSha.insets = new Insets(0, 0, 5, 0);
      gbc_cbKenshuuSha.gridx = 1;
      gbc_cbKenshuuSha.gridy = 10;
      bukkenPanel.add(cbKenshuuSha, gbc_cbKenshuuSha);

      JLabel lblNewLabel_13 = new JLabel("検収状況：");
      GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
      gbc_lblNewLabel_13.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_13.gridx = 0;
      gbc_lblNewLabel_13.gridy = 11;
      bukkenPanel.add(lblNewLabel_13, gbc_lblNewLabel_13);

      cbKenshuuJoukyou = new JComboBox();
      GridBagConstraints gbc_cbKenshuuJoukyou = new GridBagConstraints();
      gbc_cbKenshuuJoukyou.fill = GridBagConstraints.HORIZONTAL;
      gbc_cbKenshuuJoukyou.insets = new Insets(0, 0, 5, 0);
      gbc_cbKenshuuJoukyou.gridx = 1;
      gbc_cbKenshuuJoukyou.gridy = 11;
      bukkenPanel.add(cbKenshuuJoukyou, gbc_cbKenshuuJoukyou);

      JLabel lblNewLabel_15 = new JLabel("ミス件数：");
      GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
      gbc_lblNewLabel_15.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_15.gridx = 0;
      gbc_lblNewLabel_15.gridy = 12;
      bukkenPanel.add(lblNewLabel_15, gbc_lblNewLabel_15);

      spMisu = new JSpinner();
      spMisu.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
      spMisu.getEditor().getComponent(0).setForeground(Color.RED);

      GridBagConstraints gbc_spMisu = new GridBagConstraints();
      gbc_spMisu.fill = GridBagConstraints.HORIZONTAL;
      gbc_spMisu.insets = new Insets(0, 0, 5, 0);
      gbc_spMisu.gridx = 1;
      gbc_spMisu.gridy = 12;
      bukkenPanel.add(spMisu, gbc_spMisu);

      JLabel lblNewLabel_17 = new JLabel("分類：");
      GridBagConstraints gbc_lblNewLabel_17 = new GridBagConstraints();
      gbc_lblNewLabel_17.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_17.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_17.gridx = 0;
      gbc_lblNewLabel_17.gridy = 13;
      bukkenPanel.add(lblNewLabel_17, gbc_lblNewLabel_17);

      cbBunrui = new JComboBox();
      GridBagConstraints gbc_cbBunrui = new GridBagConstraints();
      gbc_cbBunrui.insets = new Insets(0, 0, 5, 0);
      gbc_cbBunrui.fill = GridBagConstraints.HORIZONTAL;
      gbc_cbBunrui.gridx = 1;
      gbc_cbBunrui.gridy = 13;
      bukkenPanel.add(cbBunrui, gbc_cbBunrui);

      JLabel lblNewLabel_18 = new JLabel("品コード");
      GridBagConstraints gbc_lblNewLabel_18 = new GridBagConstraints();
      gbc_lblNewLabel_18.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_18.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_18.gridx = 0;
      gbc_lblNewLabel_18.gridy = 14;
      bukkenPanel.add(lblNewLabel_18, gbc_lblNewLabel_18);

      txtHinCode = new JTextField();
      GridBagConstraints gbc_txtHinCode = new GridBagConstraints();
      gbc_txtHinCode.insets = new Insets(0, 0, 5, 0);
      gbc_txtHinCode.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtHinCode.gridx = 1;
      gbc_txtHinCode.gridy = 14;
      bukkenPanel.add(txtHinCode, gbc_txtHinCode);
      txtHinCode.setColumns(10);

      JLabel lblNewLabel_16 = new JLabel("備考：");
      GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
      gbc_lblNewLabel_16.anchor = GridBagConstraints.WEST;
      gbc_lblNewLabel_16.insets = new Insets(0, 0, 0, 5);
      gbc_lblNewLabel_16.gridx = 0;
      gbc_lblNewLabel_16.gridy = 15;
      bukkenPanel.add(lblNewLabel_16, gbc_lblNewLabel_16);

      JScrollPane scrollPane_3 = new JScrollPane();
      GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
      gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
      gbc_scrollPane_3.gridx = 1;
      gbc_scrollPane_3.gridy = 15;
      bukkenPanel.add(scrollPane_3, gbc_scrollPane_3);

      txtBikou = new JTextArea();
      scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane_3.setViewportView(txtBikou);
      scrollPane_3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
      txtBikou.setRows(3);
      txtBikou.setLineWrap(true);

      JSplitPane footerPanel = new JSplitPane();
      contentPane.add(footerPanel, BorderLayout.SOUTH);
      footerPanel.setDividerSize(0);
      JPanel buttonPanel = new JPanel();
      FlowLayout flowLayout_1 = (FlowLayout) buttonPanel.getLayout();
      flowLayout_1.setAlignment(FlowLayout.TRAILING);
      footerPanel.setRightComponent(buttonPanel);
      
      btnLock = new JButton("");
      btnLock.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		setFormEditable(!editable);
      	}
      });
      btnLock.setFocusPainted(false);
      btnLock.setContentAreaFilled(false);
      btnLock.setBorderPainted(false);
      btnLock.setRequestFocusEnabled(false);
      btnLock.setOpaque(false);
      btnLock.setIcon(new ImageIcon(BecchuuDetailsForm.class.getResource("/resources/icon/Lock.png")));
      buttonPanel.add(btnLock);

      JButton btnCancel = new JButton("キャンセル");
      buttonPanel.add(btnCancel);

      JButton btnOK = new JButton("実行");
      buttonPanel.add(btnOK);
      btnOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            btnOKActionPerformed(e);
         }
      });
      btnCancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      JPanel navigationPanel = new JPanel();
      FlowLayout flowLayout_2 = (FlowLayout) navigationPanel.getLayout();
      flowLayout_2.setAlignment(FlowLayout.LEADING);
      footerPanel.setLeftComponent(navigationPanel);

      JButton btnNewButton = new JButton("");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            delegate.movePrevious(BecchuuDetailsForm.this);
         }
      });
      btnNewButton.setFocusPainted(false);
      btnNewButton.setContentAreaFilled(false);
      btnNewButton.setBorderPainted(false);
      btnNewButton.setIcon(new ImageIcon(BecchuuDetailsForm.class.getResource("/resources/icon/icon-back.png")));
      navigationPanel.add(btnNewButton);

      JButton button = new JButton("");
      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            delegate.moveNext(BecchuuDetailsForm.this);
         }
      });
      button.setIcon(new ImageIcon(BecchuuDetailsForm.class.getResource("/resources/icon/icon-forward.png")));
      button.setFocusPainted(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      navigationPanel.add(button);

   }

   public void showDialog(BecchuuDetailsDelegate delegate) {
      this.delegate = delegate;
      this.getRootPane().registerKeyboardAction(e -> {
         if (DialogHelpers.showConfirmMessage("確認", "この画面閉じてよろしいですか。", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.dispose();
         }
      }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
      this.setVisible(true);
   }

   public void setBecchuuToEdit(Becchuu becchuu) {
      this.becchuuToEdit = becchuu;
      populateData();
   }

   /**
    * Create the frame.
    */
   public BecchuuDetailsForm(Becchuu becchuu) {
      setTitle("別注修正");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      //setModalityType(ModalityType.APPLICATION_MODAL);
      this.becchuuToEdit = becchuu;
      createAndSetupGUI();
      loadBecchuuEmployeesCombobox();    
      loadBunruiCombobox();
      loadBecchuuStatusCombobox();
      populateData();

      pack();
      setBounds(100, 100, 950, (int) this.getPreferredSize().getHeight());
   }
   
   private void setFormEditable(boolean editable) {
	   this.editable = editable;
	   txtBecchuuKigou.setEditable(editable);
	   txtBecchuuParameter.setEditable(editable);
	   txtMotozuKigou.setEditable(editable);	   
	   txtMotozuParameter.setEditable(editable);
	   txtBecchuuNaiyou.setEditable(editable);
	   
	   cbsakuSeiSha.setEnabled(editable);
	   cbKenshuuSha.setEnabled(editable);
	  
	   
	   if (!editable) {
		   btnLock.setIcon(new ImageIcon(BecchuuDetailsForm.class.getResource("/resources/icon/Lock.png")));
	   } else {
		   btnLock.setIcon(new ImageIcon(BecchuuDetailsForm.class.getResource("/resources/icon/unlock.png")));
	   }
   }
}