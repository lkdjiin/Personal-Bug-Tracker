/*
 *  This file is part of Personal Bug Tracker.
 *  Copyright (C) 2009, Xavier Nayrac
 *
 *  Personal Bug Tracker is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package desktopbugtracker;

import desktopbugtracker.dialog.*;
import desktopbugtracker.data.*;
import desktopbugtracker.component.*;
import desktopbugtracker.dao.*;
import desktopbugtracker.export.*;
import desktopbugtracker.facade.ProjectFacade;
import desktopbugtracker.renderer.*;
import desktopbugtracker.tools.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

public class MainUI extends javax.swing.JFrame {

    private DefaultComboBoxModel cbPriorityModel;
    private DefaultComboBoxModel cbProjectModel;
    private DefaultComboBoxModel cbBugModel;
    private DefaultComboBoxModel cbCategoryModel;
    private Vector<JXTitledPanel> panelListForMessagesPanel = new Vector<JXTitledPanel>();
    private HashMap<Integer, JTextArea> keyMsgIdValueNewMsgText = new HashMap<Integer, JTextArea>();

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainUI();
            }
        });
    }

    public MainUI() {
        new ApplicationDirectory();
        ApplicationConfig.loadProperties();
        initConnexion();
        new Database().checkVersion();
        initSwing();
        showThisFrame();
    }

    private void initConnexion() {
        try {
            Sqlite.setup(Db.DATABASE, Db.NORMAL_USE);
        } catch (SQLException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private void initSwing() {
        initModels();
        initComponents();
        initAfterSwing();
    }

    private void showThisFrame() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initModels() {
        cbPriorityModel = new DefaultComboBoxModel();
        addPrioritiesToModel();
        cbProjectModel = new DefaultComboBoxModel();
        cbBugModel = new DefaultComboBoxModel();
        cbCategoryModel = new DefaultComboBoxModel();
    }

    private void addPrioritiesToModel() {
        for (Priority e : Priority.read()) {
            cbPriorityModel.addElement(e);
        }
    }

    private void initAfterSwing() {
        jXTitledPanel1.setBorder(new DropShadowBorder(true));
        clearLabels();
        populateProject();
        populateCategory();
        populateBug();
        setTitle(ApplicationVersion.getFullName());
    }

    private void clearLabels() {
        lblBugId.setText("");
        lblPriority.setText("");
        lblOpenedVersion.setText("");
    }

    private void populateProject() {
        Vector<Project> projects = Project.readWithoutArchive();
        cbProjectModel.removeAllElements();
        for (Project e : projects) {
            cbProjectModel.addElement(e);
        }
    }

    private void populateCategory() {
        Project project = getProjectFromCombobox();
        Vector<Category> categoriesForThisProject = Category.read(project);
        if (project != null && categoriesForThisProject.isEmpty()) {
            cbCategoryModel.removeAllElements();
            cbCategory.setEnabled(false);
        } else if (project != null) {
            cbCategory.setEnabled(true);
            cbCategoryModel.removeAllElements();
            for (Category e : categoriesForThisProject) {
                cbCategoryModel.addElement(e);
            }
        }
    }

    private Project getProjectFromCombobox() {
        Project project = null;
        try {
            project = (Project) cbProjectModel.getSelectedItem();
        } catch (NullPointerException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (project == null) {
                project = new Project("", 0);
            }
        }
        return project;
    }

    private Priority getPriorityFormCombobox() {
        Priority priority = null;
        try {
            priority = (Priority) cbPriorityModel.getSelectedItem();
        } catch (NullPointerException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return priority;
    }

    private Category getCategoryFromCombobox() {
        Category category = null;
        try {
            if (cbCategory.isEnabled()) {
                category = (Category) cbCategoryModel.getSelectedItem();
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    private void populateBug() {
        Project project = getProjectFromCombobox();
        Priority priority = getPriorityFormCombobox();
        Category category = getCategoryFromCombobox();
        if (project != null && priority != null) {
            cbBugModel.removeAllElements();
            addBugsToModel(project, priority, category);
            writeMessages();
        }
    }

    private void addBugsToModel(Project proj, Priority prio, Category cat) {
        for (Bug b : Bug.read("pro_name", proj.pro_name)) {
            if (b.isOpened() && b.isGoodPriorityForPrinting(prio) && b.isGoodCategoryForPrinting(cat)) {
                cbBugModel.addElement(b);
            }
        }
    }

    private void writeMessages() {
        if (cbBugModel.getSize() == 0) {
            blankMessage();
        } else {
            constructAndShowMessages(((Bug) cbBugModel.getElementAt(0)).bug_id);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btSaveMessage = new javax.swing.JButton();
        cbProject = new javax.swing.JComboBox();
        cbPriority = new javax.swing.JComboBox();
        cbBug = new javax.swing.JComboBox();
        scrollPaneMessages = new javax.swing.JScrollPane();
        panelMessages = new javax.swing.JPanel();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jLabel3 = new javax.swing.JLabel();
        lblBugId = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblPriority = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblOpenedVersion = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuFileNewBug = new javax.swing.JMenuItem();
        menuFileCloseBug = new javax.swing.JMenuItem();
        menuFileAddMessage = new javax.swing.JMenuItem();
        menuFileChangePriority = new javax.swing.JMenuItem();
        menuFileChangeCategory = new javax.swing.JMenuItem();
        menuFileDeleteBug = new javax.swing.JMenuItem();
        menuFileFindABug = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        menuFileNewProject = new javax.swing.JMenuItem();
        menuFileNewVersion = new javax.swing.JMenuItem();
        menuFileNewCategory = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        menuFileDeleteProject = new javax.swing.JMenuItem();
        menuFileDeleteVersion = new javax.swing.JMenuItem();
        menuFileDeleteCategory = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        menuFileQuit = new javax.swing.JMenuItem();
        menuExport = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        menuExportPdfAllBugs = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        menuExportPdfOpenedBugsVersion = new javax.swing.JMenuItem();
        menuExportPdfClosedBugsVersion = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        menuExportPdfOpenedBugs = new javax.swing.JMenuItem();
        menuExportPdfClosedBugs = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JSeparator();
        menuExportPdfOpenedBugsPriority = new javax.swing.JMenuItem();
        menuExportPdfClosedBugsPriority = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JSeparator();
        menuExportPdfAllArchivedProjects = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        jMenu4 = new javax.swing.JMenu();
        menuExportAllBugs = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        menuExportOpenedBugsVersion = new javax.swing.JMenuItem();
        menuExportClosedBugsVersion = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        menuExportOpenedBugs = new javax.swing.JMenuItem();
        menuExportClosedBugs = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        menuExportOpenedBugsPriority = new javax.swing.JMenuItem();
        menuExportClosedBugsPriority = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuToolsArchiveProject = new javax.swing.JMenuItem();
        menuToolsLoadProject = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JSeparator();
        menuToolsChooseLanguage = new javax.swing.JMenuItem();
        menuMisc = new javax.swing.JMenu();
        menuMiscChangeProjectName = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuHelpHelp = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuHelpTutorials01 = new javax.swing.JMenuItem();
        menuHelpTutorials02 = new javax.swing.JMenuItem();
        menuHelpWeb = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        menuHelpAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Personal Bug Tracker 0.7");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/bug16.png")).getImage());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/toolbar/bug_new32.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("desktopbugtracker/Bundle", ApplicationConfig.getLocale()); // NOI18N
        jButton1.setToolTipText(bundle.getString("New_bug")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/toolbar/bug_close32.png"))); // NOI18N
        jButton2.setToolTipText(bundle.getString("Close_bug")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/toolbar/message32.png"))); // NOI18N
        jButton3.setToolTipText(bundle.getString("Add_message")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/toolbar/priority32.png"))); // NOI18N
        jButton4.setToolTipText(bundle.getString("Change_priority")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator5);

        btSaveMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/toolbar/filesave.png"))); // NOI18N
        btSaveMessage.setToolTipText(bundle.getString("Save_messages")); // NOI18N
        btSaveMessage.setEnabled(false);
        btSaveMessage.setFocusable(false);
        btSaveMessage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSaveMessage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btSaveMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveMessageActionPerformed(evt);
            }
        });
        jToolBar1.add(btSaveMessage);

        cbProject.setModel(cbProjectModel);
        cbProject.setRenderer(new ProjectRenderer());
        cbProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProjectActionPerformed(evt);
            }
        });

        cbPriority.setModel(cbPriorityModel);
        cbPriority.setRenderer(new PriorityRenderer());
        cbPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPriorityActionPerformed(evt);
            }
        });

        cbBug.setModel(cbBugModel);
        cbBug.setRenderer(new BugRenderer());
        cbBug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBugActionPerformed(evt);
            }
        });

        scrollPaneMessages.setBorder(null);
        scrollPaneMessages.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneMessages.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelMessages.setLayout(new javax.swing.BoxLayout(panelMessages, javax.swing.BoxLayout.Y_AXIS));
        scrollPaneMessages.setViewportView(panelMessages);

        jXTitledPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText(bundle.getString("Bug_ID_:_")); // NOI18N

        lblBugId.setText("123456");

        jLabel1.setText(bundle.getString("Priority_:")); // NOI18N

        lblPriority.setText("jLabel2");

        jLabel6.setText(bundle.getString("Opened_in_:_")); // NOI18N

        lblOpenedVersion.setText("jLabel7");

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBugId)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPriority)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOpenedVersion)
                .addContainerGap(394, Short.MAX_VALUE))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblBugId)
                    .addComponent(jLabel1)
                    .addComponent(lblPriority)
                    .addComponent(jLabel6)
                    .addComponent(lblOpenedVersion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbCategory.setModel(cbCategoryModel);
        cbCategory.setRenderer(new CategoryRenderer());
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        jMenu1.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("File").charAt(0));
        jMenu1.setText(bundle.getString("File")); // NOI18N
        jMenu1.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu1MenuSelected(evt);
            }
        });

        menuFileNewBug.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuFileNewBug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/menu/bug_new16.png"))); // NOI18N
        menuFileNewBug.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileNewBug").charAt(0));
        menuFileNewBug.setText(bundle.getString("New_bug...")); // NOI18N
        menuFileNewBug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewBugActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileNewBug);

        menuFileCloseBug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/menu/bug_close16.png"))); // NOI18N
        menuFileCloseBug.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileCloseBug").charAt(0));
        menuFileCloseBug.setText(bundle.getString("Close_bug")); // NOI18N
        menuFileCloseBug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileCloseBugActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileCloseBug);

        menuFileAddMessage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        menuFileAddMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/menu/message16.png"))); // NOI18N
        menuFileAddMessage.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileAddMessage").charAt(0));
        menuFileAddMessage.setText(bundle.getString("Add_message...")); // NOI18N
        menuFileAddMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileAddMessageActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileAddMessage);

        menuFileChangePriority.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuFileChangePriority.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/menu/priority16.png"))); // NOI18N
        menuFileChangePriority.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileChangePriority").charAt(0));
        menuFileChangePriority.setText(bundle.getString("Change_priority...")); // NOI18N
        menuFileChangePriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileChangePriorityActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileChangePriority);

        menuFileChangeCategory.setText(bundle.getString("Change_category")); // NOI18N
        menuFileChangeCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileChangeCategoryActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileChangeCategory);

        menuFileDeleteBug.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileDeleteBug").charAt(0));
        menuFileDeleteBug.setText(bundle.getString("Delete_bug")); // NOI18N
        menuFileDeleteBug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileDeleteBugActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileDeleteBug);

        menuFileFindABug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/menu/find.png"))); // NOI18N
        menuFileFindABug.setText(bundle.getString("Menu_Find_a_bug")); // NOI18N
        menuFileFindABug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileFindABugActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileFindABug);
        jMenu1.add(jSeparator1);

        menuFileNewProject.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileNewProject").charAt(0));
        menuFileNewProject.setText(bundle.getString("New_project...")); // NOI18N
        menuFileNewProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewProjectActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileNewProject);

        menuFileNewVersion.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileNewVersion").charAt(0));
        menuFileNewVersion.setText(bundle.getString("New_version...")); // NOI18N
        menuFileNewVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewVersionActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileNewVersion);

        menuFileNewCategory.setText(bundle.getString("New_category")); // NOI18N
        menuFileNewCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewCategoryActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileNewCategory);
        jMenu1.add(jSeparator13);

        menuFileDeleteProject.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileDeleteProject").charAt(0));
        menuFileDeleteProject.setText(bundle.getString("Delete_project")); // NOI18N
        menuFileDeleteProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileDeleteProjectActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileDeleteProject);

        menuFileDeleteVersion.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileDeleteVersion").charAt(0));
        menuFileDeleteVersion.setText(bundle.getString("Delete_version...")); // NOI18N
        menuFileDeleteVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileDeleteVersionActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileDeleteVersion);

        menuFileDeleteCategory.setText(bundle.getString("Delete_category")); // NOI18N
        menuFileDeleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileDeleteCategoryActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileDeleteCategory);
        jMenu1.add(jSeparator2);

        menuFileQuit.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("FileQuit").charAt(0));
        menuFileQuit.setText(bundle.getString("Quit")); // NOI18N
        menuFileQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileQuitActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileQuit);

        jMenuBar1.add(jMenu1);

        menuExport.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("Print").charAt(0));
        menuExport.setText(bundle.getString("Print")); // NOI18N

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/desktopbugtracker/img/menu/pdf.png"))); // NOI18N
        jMenu3.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdf").charAt(0));
        jMenu3.setText("Pdf");

        menuExportPdfAllBugs.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfAll").charAt(0));
        menuExportPdfAllBugs.setText(bundle.getString("All_bugs...")); // NOI18N
        menuExportPdfAllBugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfAllBugsActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfAllBugs);
        jMenu3.add(jSeparator9);

        menuExportPdfOpenedBugsVersion.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfOpenedVersion").charAt(0));
        menuExportPdfOpenedBugsVersion.setText(bundle.getString("Opened_bugs_in_one_version")); // NOI18N
        menuExportPdfOpenedBugsVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfOpenedBugsVersionActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfOpenedBugsVersion);

        menuExportPdfClosedBugsVersion.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfClosedVersion").charAt(0));
        menuExportPdfClosedBugsVersion.setText(bundle.getString("Closed_bugs_in_one_version")); // NOI18N
        menuExportPdfClosedBugsVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfClosedBugsVersionActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfClosedBugsVersion);
        jMenu3.add(jSeparator10);

        menuExportPdfOpenedBugs.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfOpened").charAt(0));
        menuExportPdfOpenedBugs.setText(bundle.getString("Opened_bugs...")); // NOI18N
        menuExportPdfOpenedBugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfOpenedBugsActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfOpenedBugs);

        menuExportPdfClosedBugs.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfClosed").charAt(0));
        menuExportPdfClosedBugs.setText(bundle.getString("Closed_bugs...")); // NOI18N
        menuExportPdfClosedBugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfClosedBugsActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfClosedBugs);
        jMenu3.add(jSeparator11);

        menuExportPdfOpenedBugsPriority.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfOpenedPriority").charAt(0));
        menuExportPdfOpenedBugsPriority.setText(bundle.getString("Opened_bugs_of_one_priority...")); // NOI18N
        menuExportPdfOpenedBugsPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfOpenedBugsPriorityActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfOpenedBugsPriority);

        menuExportPdfClosedBugsPriority.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfClosedPriority").charAt(0));
        menuExportPdfClosedBugsPriority.setText(bundle.getString("Closed_bugs_of_one_priority...")); // NOI18N
        menuExportPdfClosedBugsPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfClosedBugsPriorityActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfClosedBugsPriority);
        jMenu3.add(jSeparator14);

        menuExportPdfAllArchivedProjects.setText(bundle.getString("Archived_project")); // NOI18N
        menuExportPdfAllArchivedProjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportPdfAllArchivedProjectsActionPerformed(evt);
            }
        });
        jMenu3.add(menuExportPdfAllArchivedProjects);

        menuExport.add(jMenu3);
        menuExport.add(jSeparator12);

        jMenu4.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintText").charAt(0));
        jMenu4.setText(bundle.getString("Text")); // NOI18N

        menuExportAllBugs.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfAll").charAt(0));
        menuExportAllBugs.setText(bundle.getString("All_bugs...")); // NOI18N
        menuExportAllBugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportAllBugsActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportAllBugs);
        jMenu4.add(jSeparator6);

        menuExportOpenedBugsVersion.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfOpenedVersion").charAt(0));
        menuExportOpenedBugsVersion.setText(bundle.getString("Opened_bugs_in_one_version")); // NOI18N
        menuExportOpenedBugsVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportOpenedBugsVersionActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportOpenedBugsVersion);

        menuExportClosedBugsVersion.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfClosedVersion").charAt(0));
        menuExportClosedBugsVersion.setText(bundle.getString("Closed_bugs_in_one_version")); // NOI18N
        menuExportClosedBugsVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportClosedBugsVersionActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportClosedBugsVersion);
        jMenu4.add(jSeparator7);

        menuExportOpenedBugs.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfOpened").charAt(0));
        menuExportOpenedBugs.setText(bundle.getString("Opened_bugs...")); // NOI18N
        menuExportOpenedBugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportOpenedBugsActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportOpenedBugs);

        menuExportClosedBugs.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfClosed").charAt(0));
        menuExportClosedBugs.setText(bundle.getString("Closed_bugs...")); // NOI18N
        menuExportClosedBugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportClosedBugsActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportClosedBugs);
        jMenu4.add(jSeparator8);

        menuExportOpenedBugsPriority.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfOpenedPriority").charAt(0));
        menuExportOpenedBugsPriority.setText(bundle.getString("Opened_bugs_of_one_priority...")); // NOI18N
        menuExportOpenedBugsPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportOpenedBugsPriorityActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportOpenedBugsPriority);

        menuExportClosedBugsPriority.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("PrintPdfClosedPriority").charAt(0));
        menuExportClosedBugsPriority.setText(bundle.getString("Closed_bugs_of_one_priority...")); // NOI18N
        menuExportClosedBugsPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportClosedBugsPriorityActionPerformed(evt);
            }
        });
        jMenu4.add(menuExportClosedBugsPriority);

        menuExport.add(jMenu4);

        jMenuBar1.add(menuExport);

        jMenu2.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("Tools").charAt(0));
        jMenu2.setText(bundle.getString("Tools")); // NOI18N

        menuToolsArchiveProject.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("ToolsArchive").charAt(0));
        menuToolsArchiveProject.setText(bundle.getString("Archive_project...")); // NOI18N
        menuToolsArchiveProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsArchiveProjectActionPerformed(evt);
            }
        });
        jMenu2.add(menuToolsArchiveProject);

        menuToolsLoadProject.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("ToolsLoad").charAt(0));
        menuToolsLoadProject.setText(bundle.getString("Load_archived_project...")); // NOI18N
        menuToolsLoadProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsLoadProjectActionPerformed(evt);
            }
        });
        jMenu2.add(menuToolsLoadProject);
        jMenu2.add(jSeparator4);

        jMenuItem1.setText(bundle.getString("Delete_archive")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jSeparator15);

        menuToolsChooseLanguage.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("ToolsLanguage").charAt(0));
        menuToolsChooseLanguage.setText(bundle.getString("Choose_language...")); // NOI18N
        menuToolsChooseLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolsChooseLanguageActionPerformed(evt);
            }
        });
        jMenu2.add(menuToolsChooseLanguage);

        jMenuBar1.add(jMenu2);

        menuMisc.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("Misc").charAt(0));
        menuMisc.setText(bundle.getString("Misc")); // NOI18N

        menuMiscChangeProjectName.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("MiscChange").charAt(0));
        menuMiscChangeProjectName.setText(bundle.getString("Change_project_name")); // NOI18N
        menuMiscChangeProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMiscChangeProjectNameActionPerformed(evt);
            }
        });
        menuMisc.add(menuMiscChangeProjectName);

        jMenuBar1.add(menuMisc);

        menuHelp.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("Help").charAt(0));
        menuHelp.setText(bundle.getString("menuHelp")); // NOI18N

        menuHelpHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuHelpHelp.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("HelpHelp").charAt(0));
        menuHelpHelp.setText(bundle.getString("Help")); // NOI18N
        menuHelpHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpHelpActionPerformed(evt);
            }
        });
        menuHelp.add(menuHelpHelp);

        jMenu5.setText(bundle.getString("Tutorials")); // NOI18N

        menuHelpTutorials01.setText(bundle.getString("Quick_start")); // NOI18N
        menuHelpTutorials01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpTutorials01ActionPerformed(evt);
            }
        });
        jMenu5.add(menuHelpTutorials01);

        menuHelpTutorials02.setText(bundle.getString("Working_with_categories")); // NOI18N
        menuHelpTutorials02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpTutorials02ActionPerformed(evt);
            }
        });
        jMenu5.add(menuHelpTutorials02);

        menuHelp.add(jMenu5);

        menuHelpWeb.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("HelpWeb").charAt(0));
        menuHelpWeb.setText(bundle.getString("Doc_on_the_web")); // NOI18N
        menuHelpWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpWebActionPerformed(evt);
            }
        });
        menuHelp.add(menuHelpWeb);
        menuHelp.add(jSeparator3);

        menuHelpAbout.setMnemonic(java.util.ResourceBundle.getBundle("desktopbugtracker/Mnemonic", ApplicationConfig.getLocale()).getString("HelpAbout").charAt(0));
        menuHelpAbout.setText(bundle.getString("About")); // NOI18N
        menuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuHelpAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbBug, 0, 775, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cbProject, 0, 397, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(cbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbBug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProjectActionPerformed
        showCategoryAndBug();
    }//GEN-LAST:event_cbProjectActionPerformed

    private void showCategoryAndBug() {
        populateCategory();
        populateBug();
    }

    private void cbPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPriorityActionPerformed
        populateBug();
    }//GEN-LAST:event_cbPriorityActionPerformed

    private void cbBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBugActionPerformed
        doMessagesModification();
        if (cbBugModel.getSelectedItem() == null) {
            return;
        }
        setGuiWhenBugHasChanged();
    }//GEN-LAST:event_cbBugActionPerformed

    private void doMessagesModification() {
        if (!keyMsgIdValueNewMsgText.isEmpty()) {
            DialogModifiedMessages dial = new DialogModifiedMessages(this, true);
            int resp = dial.getReturnStatus();
            if (resp == DialogModifiedMessages.RET_SAVE) {
                btSaveMessageActionPerformed(null);
            } else if (resp == DialogModifiedMessages.RET_DO_NOT_SAVE) {
                // Do not save but continue. In fact do nothing...
            } else {
                // @todo Cancel this action (of changing bug)
                // Reprint the previous bug along with its modified messages
            }
        }
    }

    private void setGuiWhenBugHasChanged() {
        Bug bug = (Bug) cbBugModel.getSelectedItem();
        lblBugId.setText(String.valueOf(bug.bug_id));
        jXTitledPanel1.setTitle(bug.bug_title);
        lblPriority.setText(bug.getI18NDescriptionOfMyPriority());
        lblOpenedVersion.setText(bug.bug_version_opened);
        constructAndShowMessages(bug.bug_id);
    }

    private void messageKeyTyped(KeyEvent evt, int mes_id, JTextArea ta) {
        keyMsgIdValueNewMsgText.put(mes_id, ta);
        btSaveMessage.setEnabled(true);
    }

    public void constructAndShowMessages(int bug_id) {
        clearMessageRelatedComponents();
        for (Message m : Message.read("bug_id", bug_id)) {
            final JTextArea jta = makeTextAreaFor(m);
            JScrollPane scroll = makeScrollPaneFor(jta);
            JXTitledPanel titledPanelMessage = makeTitledPanelFor(m.getTitleForScreenPrinting(), scroll, m.mes_id);
            showTitledPanel(titledPanelMessage);
        }
    }

    private void showTitledPanel(JXTitledPanel tp) {
        panelListForMessagesPanel.add(tp);
        panelMessages.add(tp);
        panelMessages.validate();
    }

    private void clearMessageRelatedComponents() {
        panelMessages.removeAll();
        panelListForMessagesPanel.removeAllElements();
        keyMsgIdValueNewMsgText.clear();
        btSaveMessage.setEnabled(false);
    }

    private JTextArea makeTextAreaFor(Message m) {
        final JTextArea jta = makeBasicTextArea(m.mes_text);
        final int mes_id = m.mes_id;
        jta.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent evt) {
                messageKeyTyped(evt, mes_id, jta);
            }
        });
        return jta;
    }

    private JTextArea makeBasicTextArea(String text) {
        final JTextArea jta = new JTextArea(text);
        jta.setColumns(40);
        jta.setMargin(new Insets(10, 10, 10, 10));
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        return jta;
    }

    private JScrollPane makeScrollPaneFor(JTextArea jta) {
        JScrollPane scroll = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        return scroll;
    }

    private JXTitledPanel makeTitledPanelFor(String title, JScrollPane scroll, final int messageId) {
        JLabel lbl = makeLabelForAttachment(messageId);
        return makeTitledPane(title, scroll, lbl);
    }

    private JLabel makeLabelForAttachment(final int messageId) {
        JLabel lbl = new JLabel(ResourceBundle.getBundle("desktopbugtracker/Bundle",
                ApplicationConfig.getLocale()).getString("Attachment") + " ");
        lbl = labelAttachmentSetColor(lbl, messageId);
        lbl = labelAttachmentAddListener(lbl, messageId);
        return lbl;
    }

    private JLabel labelAttachmentSetColor(JLabel lbl, final int messageId) {
        Vector<Attachment> v = new Attachment().readFromMessage(messageId);
        if (v != null && v.size() > 0) {
            lbl.setForeground(Color.yellow);
        } else {
            lbl.setForeground(Color.white);
        }
        return lbl;
    }

    private JLabel labelAttachmentAddListener(JLabel lbl, final int messageId) {
        lbl.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                labelAttachmentMouseClicked(e, messageId);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelAttachmentMouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelAttachmentMouseExited();
            }
        });
        return lbl;
    }

    private JXTitledPanel makeTitledPane(String title, JScrollPane scroll, JLabel lbl) {
        JXTitledPanel tp = new JXTitledPanel(title, scroll);
        tp.setBorder(new DropShadowBorder(true));
        tp.setRightDecoration(lbl);
        return tp;
    }

    private void labelAttachmentMouseClicked(MouseEvent e, int mes_id) {
        DialogAttachment dial = new DialogAttachment(this, true, mes_id);
    }

    private void labelAttachmentMouseEntered() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void labelAttachmentMouseExited() {
        setCursor(null);
    }

    private void blankMessage() {
        clearMessageRelatedComponents();
        lblBugId.setText(" ");
        lblOpenedVersion.setText(" ");
        jXTitledPanel1.setTitle(I18N.getBundle("No_bug_to_show"));
        JXTitledPanel jxtpMessage = new JXTitledPanel(I18N.getBundle("No_message_to_show"));
        jxtpMessage.setBorder(new DropShadowBorder(true));
        panelMessages.add(jxtpMessage);
        panelMessages.validate();
    }

    private void menuFileNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewProjectActionPerformed
        DialogNewProject dial = new DialogNewProject(this, true);
        if (dial.getReturnStatus() == DialogNewProject.RET_OK) {
            populateProject();
        }
}//GEN-LAST:event_menuFileNewProjectActionPerformed

    private void menuFileNewVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewVersionActionPerformed
        try {
            Project project = getProjectFromCombobox();
            new DialogNewVersion(this, true, project);
        } catch (SQLException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_menuFileNewVersionActionPerformed

    private void menuFileNewBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewBugActionPerformed
        try {
            Priority priority = getPriorityFormCombobox();
            Project project = getProjectFromCombobox();
            new DialogNewReport(this, true, priority, project);
            populateBug();
        } catch (SQLException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuFileNewBugActionPerformed

    private void menuFileAddMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileAddMessageActionPerformed
        if (cbBugModel.getSelectedItem() == null) {
            return;
        }
        Bug bug = (Bug) cbBugModel.getSelectedItem();
        new DialogAddMessage(this, true, bug.bug_id);
        constructAndShowMessages(bug.bug_id);
    }//GEN-LAST:event_menuFileAddMessageActionPerformed

    private void menuFileChangePriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileChangePriorityActionPerformed
        DialogChangePriority dial = new DialogChangePriority(this, true, DialogChangePriority.MODE_CHANGE);
        if (dial.getReturnStatus() == DialogChangePriority.RET_OK) {
            Priority priority = dial.getNewPriority();
            Bug bug = (Bug) cbBugModel.getSelectedItem();
            bug.pri_number = priority.pri_number;
            bug.updatePk();
            populateBug();
        }
    }//GEN-LAST:event_menuFileChangePriorityActionPerformed

    private void menuFileCloseBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileCloseBugActionPerformed
        if (cbBugModel.getSelectedItem() == null) {
            return;
        }
        Bug bug = (Bug) cbBugModel.getSelectedItem();
        DialogCloseBug dial = new DialogCloseBug(this, true, bug);
        if (dial.getReturnStatus() == DialogCloseBug.RET_OK) {
            populateBug();
        }
    }//GEN-LAST:event_menuFileCloseBugActionPerformed

    private void menuFileDeleteBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileDeleteBugActionPerformed
        if (cbBugModel.getSelectedItem() == null) {
            return;
        }
        Bug bug = (Bug) cbBugModel.getSelectedItem();
        String msg = I18N.getBundle("Do_you_really_want_to_definitively_delete_this_bug");
        msg += bug.bug_title + I18N.getBundle("from_project_") + " " + bug.pro_name;
        if (JOptionPane.showConfirmDialog(this, msg) == JOptionPane.YES_OPTION) {
            int bug_id = bug.bug_id;
            bug.deletePk();
            Message.delete("bug_id", bug_id);
            populateBug();
        }
    }//GEN-LAST:event_menuFileDeleteBugActionPerformed

    private void menuFileDeleteProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileDeleteProjectActionPerformed
        Project project = (Project) cbProjectModel.getSelectedItem();
        int answer = JOptionPane.showConfirmDialog(this, I18N.getBundle("Really_delete_project_") + " " +
                project.pro_name + " ?");
        if (answer == JOptionPane.YES_OPTION) {
            try {
                ProjectFacade.delete(project);
            } catch (SqlTransactionException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuFileDeleteProjectActionPerformed

    private void showProjectAndBug() {
        populateProject();
        populateBug();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        menuFileNewBugActionPerformed(evt);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        menuFileCloseBugActionPerformed(evt);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        menuFileAddMessageActionPerformed(evt);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void menuFileQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuFileQuitActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        menuFileChangePriorityActionPerformed(evt);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void exportToPdf(TypeOfBug typeOfBug) {
        exportToPdf(typeOfBug, null);
    }

    private void exportToPdf(TypeOfBug typeOfBug, Version version) {
        try {
            String filename = chooseFilenameFor("pdf");
            ProjectPaperData ppd = new ProjectPaperData(getProjectFromCombobox(), version, typeOfBug,
                    getPriorityFromTypeOfBug(typeOfBug));
            PdfExport pdfExport = new PdfExport(filename, ppd);
            pdfExport.write();
        } catch (PdfExportException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String chooseFilenameFor(String fileType) {
        ExportChooser expChooser = new ExportChooser(fileType, this);
        expChooser.choose();
        return expChooser.getFilename();
    }

    private Priority getPriorityFromTypeOfBug(TypeOfBug typeOfBug) {
        if (typeOfBug == TypeOfBug.OPENED_BUGS_OF_ONE_PRIORITY || typeOfBug == TypeOfBug.CLOSED_BUGS_OF_ONE_PRIORITY) {
            DialogChangePriority dial = new DialogChangePriority(this, true, DialogChangePriority.MODE_CHOOSE);
            if (dial.getReturnStatus() == DialogChangePriority.RET_OK) {
                return dial.getNewPriority();
            }
        }
        return new Priority(0);
    }

    private void exportToText(TypeOfBug typeOfBug) {
        exportToText(typeOfBug, null);
    }

    private void exportToText(TypeOfBug typeOfBug, Version version) {
        String filename = chooseFilenameFor("txt");
        ProjectPaperData ppd = new ProjectPaperData(getProjectFromCombobox(), version, typeOfBug,
                getPriorityFromTypeOfBug(typeOfBug));
        try {
            TextExport textExport = new TextExport(filename, ppd);
            textExport.write();
        } catch (TextExportException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuExportAllBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportAllBugsActionPerformed
        exportToText(TypeOfBug.ALL_BUGS);
}//GEN-LAST:event_menuExportAllBugsActionPerformed

    private void menuExportOpenedBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportOpenedBugsActionPerformed
        exportToText(TypeOfBug.OPENED_BUGS);
    }//GEN-LAST:event_menuExportOpenedBugsActionPerformed

    private void menuExportClosedBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportClosedBugsActionPerformed
        exportToText(TypeOfBug.CLOSED_BUGS);
    }//GEN-LAST:event_menuExportClosedBugsActionPerformed

    private void menuExportOpenedBugsPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportOpenedBugsPriorityActionPerformed
        exportToText(TypeOfBug.OPENED_BUGS_OF_ONE_PRIORITY);
    }//GEN-LAST:event_menuExportOpenedBugsPriorityActionPerformed

    private void menuExportClosedBugsPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportClosedBugsPriorityActionPerformed
        exportToText(TypeOfBug.CLOSED_BUGS_OF_ONE_PRIORITY);
    }//GEN-LAST:event_menuExportClosedBugsPriorityActionPerformed

    private void menuFileDeleteVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileDeleteVersionActionPerformed
        Project project = getProjectFromCombobox();
        DialogChooseVersion dial = new DialogChooseVersion(this, true, project);
        if (dial.getReturnStatus() == DialogChooseVersion.RET_OK) {
            Version toDelete = dial.getVersion();
            Vector<Bug> vBug = new Vector<Bug>();
            try {
                vBug = BugDAO.read("pro_name", project.pro_name);
            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean usedVersion = false;
            for (Bug e : vBug) {
                if (e.bug_version_opened.equals(toDelete.ver_name) || e.bug_version_closed.equals(toDelete.ver_name)) {
                    usedVersion = true;
                }
            }
            if (usedVersion == true) {
                JOptionPane.showMessageDialog(this, I18N.getBundle("This_version_is_used"));
            } else {
                int response = JOptionPane.showConfirmDialog(this, I18N.getBundle("Really_delete_version_") + " " + toDelete.ver_name + " ?");
                if (response == JOptionPane.OK_OPTION) {
                    try {
                        VersionDAO.deletePk(toDelete);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }//GEN-LAST:event_menuFileDeleteVersionActionPerformed

    private void menuToolsArchiveProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsArchiveProjectActionPerformed
        new DialogArchive(this, (Project) cbProjectModel.getSelectedItem());
        showProjectAndBug();
    }//GEN-LAST:event_menuToolsArchiveProjectActionPerformed

    private void menuToolsLoadProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsLoadProjectActionPerformed
        DialogChooseArchivedProject dial = new DialogChooseArchivedProject(this);
        if (dial.getReturnStatus() == DialogChooseArchivedProject.RET_OK) {
            String projectName = dial.getProject().pro_name;
            try {
                if (ProjectDAO.exists(projectName) && !ProjectDAO.isArchived(projectName)) {
                    JOptionPane.showMessageDialog(this, I18N.getBundle("A_working_project_with_same_name"));
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean success = false;
            try {
                Archiving archiving = new Archiving(new Project(projectName, 1));
                success = archiving.loadArchivedProject();
            } catch (SqlTransactionException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                showProjectAndBug();
            } else {
                JOptionPane.showMessageDialog(this, I18N.getBundle("Error_:_unabled_to_load_the_archived_project."));
            }
        }
    }//GEN-LAST:event_menuToolsLoadProjectActionPerformed

    private void menuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpAboutActionPerformed
        new DialogAbout(this, true);
    }//GEN-LAST:event_menuHelpAboutActionPerformed

    private void menuToolsChooseLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuToolsChooseLanguageActionPerformed
        DialogChooseLanguage dial = new DialogChooseLanguage(this, true);
        if (dial.getReturnStatus() == DialogChooseLanguage.RET_OK) {
            Locale l = dial.getLanguage();
            ApplicationConfig.setLocale(l);
            ApplicationConfig.saveProperties();
            JOptionPane.showMessageDialog(this, I18N.getBundle("close_and_restart"));
        }
    }//GEN-LAST:event_menuToolsChooseLanguageActionPerformed

    private void menuHelpHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpHelpActionPerformed
        try {
            Desktop.getDesktop().open(new File("doc/Reference.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuHelpHelpActionPerformed

    private void btSaveMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveMessageActionPerformed
        for (Integer i : keyMsgIdValueNewMsgText.keySet()) {
            String newMsg = ((JTextArea) keyMsgIdValueNewMsgText.get(i)).getText();
            try {
                Message msg = MessageDAO.readPk(i);
                msg.mes_text = newMsg;
                MessageDAO.updatePk(msg);
            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error : Unable to update message.");
            }
        }

        keyMsgIdValueNewMsgText.clear();
        btSaveMessage.setEnabled(false);
    }//GEN-LAST:event_btSaveMessageActionPerformed

    private void menuExportOpenedBugsVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportOpenedBugsVersionActionPerformed
        DialogChooseVersion dial = new DialogChooseVersion(null, true, getProjectFromCombobox());
        if (dial.getReturnStatus() != DialogChooseVersion.RET_OK) {
            return;
        }
        exportToText(TypeOfBug.OPENED_BUGS_IN_A_VERSION, dial.getVersion());
    }//GEN-LAST:event_menuExportOpenedBugsVersionActionPerformed

    private void menuExportClosedBugsVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportClosedBugsVersionActionPerformed
        DialogChooseVersion dial = new DialogChooseVersion(null, true, getProjectFromCombobox());
        if (dial.getReturnStatus() != DialogChooseVersion.RET_OK) {
            return;
        }
        exportToText(TypeOfBug.CLOSED_BUGS_IN_A_VERSION, dial.getVersion());
    }//GEN-LAST:event_menuExportClosedBugsVersionActionPerformed

    private void menuMiscChangeProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMiscChangeProjectNameActionPerformed
        String newName;
        newName = JOptionPane.showInputDialog(this, I18N.getBundle("Misc0000"));
        if (newName == null) {
            return;
        }
        Project project = (Project) cbProjectModel.getSelectedItem();
        try {
            boolean projExists = ProjectDAO.exists(newName);
            if (projExists == true) {
                JOptionPane.showMessageDialog(this, I18N.getBundle("Misc0100"));
                return;
            }
            String msg = I18N.getBundle("Misc0200") + " :";
            msg += "\n";
            msg += I18N.getBundle("Misc0300") + " : " + project.pro_name + "\n";
            msg += I18N.getBundle("Misc0400") + " : " + newName;
            int resp = JOptionPane.showConfirmDialog(this, msg);
            if (resp == JOptionPane.OK_OPTION) {
                try {
                    if (ProjectFacade.rename(project, newName) == true) {
                        JOptionPane.showMessageDialog(this, I18N.getBundle("Misc0500"));
                        showProjectAndBug();
                    } else {
                        JOptionPane.showMessageDialog(this, I18N.getBundle("Misc0600"));
                    }
                } catch (SqlTransactionException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                return;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMiscChangeProjectNameActionPerformed

    private void menuHelpWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpWebActionPerformed
        try {
            Desktop.getDesktop().browse(new URI(
                    "http://personalbugtracker.free.fr/documentation/doc-index.php"));
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuHelpWebActionPerformed

    private void menuExportPdfAllBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfAllBugsActionPerformed
        exportToPdf(TypeOfBug.ALL_BUGS);
    }//GEN-LAST:event_menuExportPdfAllBugsActionPerformed

    private void menuExportPdfOpenedBugsVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfOpenedBugsVersionActionPerformed
        DialogChooseVersion dial = new DialogChooseVersion(null, true, getProjectFromCombobox());
        if (dial.getReturnStatus() != DialogChooseVersion.RET_OK) {
            return;
        }
        exportToPdf(TypeOfBug.OPENED_BUGS_IN_A_VERSION, dial.getVersion());
    }//GEN-LAST:event_menuExportPdfOpenedBugsVersionActionPerformed

    private void menuExportPdfClosedBugsVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfClosedBugsVersionActionPerformed
        DialogChooseVersion dial = new DialogChooseVersion(null, true, getProjectFromCombobox());
        if (dial.getReturnStatus() != DialogChooseVersion.RET_OK) {
            return;
        }
        exportToPdf(TypeOfBug.CLOSED_BUGS_IN_A_VERSION, dial.getVersion());
    }//GEN-LAST:event_menuExportPdfClosedBugsVersionActionPerformed

    private void menuExportPdfOpenedBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfOpenedBugsActionPerformed
        exportToPdf(TypeOfBug.OPENED_BUGS);
    }//GEN-LAST:event_menuExportPdfOpenedBugsActionPerformed

    private void menuExportPdfClosedBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfClosedBugsActionPerformed
        exportToPdf(TypeOfBug.CLOSED_BUGS);
    }//GEN-LAST:event_menuExportPdfClosedBugsActionPerformed

    private void menuExportPdfOpenedBugsPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfOpenedBugsPriorityActionPerformed
        exportToPdf(TypeOfBug.OPENED_BUGS_OF_ONE_PRIORITY);
    }//GEN-LAST:event_menuExportPdfOpenedBugsPriorityActionPerformed

    private void menuExportPdfClosedBugsPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfClosedBugsPriorityActionPerformed
        exportToPdf(TypeOfBug.CLOSED_BUGS_OF_ONE_PRIORITY);
    }//GEN-LAST:event_menuExportPdfClosedBugsPriorityActionPerformed

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        populateBug();
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void menuFileNewCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewCategoryActionPerformed
        try {
            Project project = null;
            if (cbProjectModel.getSelectedItem() != null) {
                project = (Project) cbProjectModel.getSelectedItem();
            }
            DialogNewCategory dial = new DialogNewCategory(this, true, project);
            if (dial.getReturnStatus() == DialogNewCategory.RET_OK) {
                showCategoryAndBug();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuFileNewCategoryActionPerformed

    private void menuFileChangeCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileChangeCategoryActionPerformed
        Bug b = (Bug) cbBugModel.getSelectedItem();
        DialogChangeCategory dial = new DialogChangeCategory(this, true, (Project) cbProjectModel.getSelectedItem(), b);

        if (dial.getReturnStatus() == DialogChangeCategory.RET_OK) {
            Category cat = dial.getValue();
            b.cat_id = cat.cat_id;
            try {
                BugDAO.updatePk(b);
            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuFileChangeCategoryActionPerformed

    private void jMenu1MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu1MenuSelected
        if (getProjectFromCombobox().pro_name.equals("")) {
            menuFileNewBug.setEnabled(false);
            menuFileCloseBug.setEnabled(false);
            menuFileAddMessage.setEnabled(false);
            menuFileChangePriority.setEnabled(false);
            menuFileChangeCategory.setEnabled(false);
            menuFileDeleteBug.setEnabled(false);
            menuFileNewVersion.setEnabled(false);
            menuFileNewCategory.setEnabled(false);
            menuFileDeleteCategory.setEnabled(false);
            menuFileDeleteProject.setEnabled(false);
            menuFileDeleteVersion.setEnabled(false);
        } else {
            menuFileNewBug.setEnabled(true);
            menuFileCloseBug.setEnabled(true);
            menuFileAddMessage.setEnabled(true);
            menuFileChangePriority.setEnabled(true);
            menuFileChangeCategory.setEnabled(true);
            menuFileDeleteBug.setEnabled(true);
            menuFileNewVersion.setEnabled(true);
            menuFileNewCategory.setEnabled(true);
            menuFileDeleteCategory.setEnabled(true);
            menuFileDeleteProject.setEnabled(true);
            menuFileDeleteVersion.setEnabled(true);
        }
        if (cbBugModel.getSelectedItem() == null) {
            menuFileDeleteBug.setEnabled(false);
            menuFileCloseBug.setEnabled(false);
            menuFileAddMessage.setEnabled(false);
            menuFileChangePriority.setEnabled(false);
        } else {
            menuFileDeleteBug.setEnabled(true);
            menuFileCloseBug.setEnabled(true);
            menuFileAddMessage.setEnabled(true);
            menuFileChangePriority.setEnabled(true);
        }
        if (cbCategory.isEnabled()) {
            menuFileChangeCategory.setEnabled(true);
            menuFileDeleteCategory.setEnabled(true);
        } else {
            menuFileChangeCategory.setEnabled(false);
            menuFileDeleteCategory.setEnabled(false);
        }
    }//GEN-LAST:event_jMenu1MenuSelected

    private void menuFileDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileDeleteCategoryActionPerformed
        Project p = (Project) cbProjectModel.getSelectedItem();
        DialogChooseCategory dial = new DialogChooseCategory(this, true, p);
        if (dial.getReturnStatus() == DialogChooseCategory.RET_OK) {
            Category toDelete = dial.getValue();
            try {
                // Regarder si la catégorie est utilisée par certains bugs
                boolean reallyDelete = false;
                if (CategoryDAO.isUsedCategory(p, toDelete)) {
                    String msg = "Warning ! Category " + toDelete.cat_title + " is used by project " + p.pro_name + " .\n";
                    msg += "Do you really want to delete it permanently ?";
                    int resp = JOptionPane.showConfirmDialog(this, msg);
                    if (resp == JOptionPane.OK_OPTION) {
                        reallyDelete = true;
                    }
                } else {
                    String msg = "Do you really want to delete the category " + toDelete.cat_title + " permanently ?";
                    int resp = JOptionPane.showConfirmDialog(this, msg);
                    if (resp == JOptionPane.OK_OPTION) {
                        reallyDelete = true;
                    }
                }
                if (reallyDelete) {
                    // Supprimer enregistrement dans pro_cat
                    // Supprimer enregistrement dans category
                    // Passer cat_id à 0 dans les bug concernés
                    Pro_catDAO.delete(new Pro_cat(p.pro_name, toDelete.cat_id, 0));
                    CategoryDAO.delete(toDelete);
                    BugDAO.turnOffCategory(p, toDelete);
                    showCategoryAndBug();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuFileDeleteCategoryActionPerformed

    private void menuHelpTutorials01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpTutorials01ActionPerformed
        try {
            Desktop.getDesktop().open(new File("doc/Tutoriel-Demarrage-Rapide.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuHelpTutorials01ActionPerformed

    private void menuHelpTutorials02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpTutorials02ActionPerformed
        try {
            Desktop.getDesktop().open(new File("doc/Tutoriel-Travaillez-avec-les-categories.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuHelpTutorials02ActionPerformed

    private void menuExportPdfAllArchivedProjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportPdfAllArchivedProjectsActionPerformed
        DialogChooseArchivedProject dial = new DialogChooseArchivedProject(this);
        if (dial.getReturnStatus() != DialogChooseArchivedProject.RET_OK) {
            return;
        }
        Project project = dial.getProject();
        String filename = chooseFilenameFor("pdf");
        ProjectPaperData ppd = new ProjectPaperData(project, null, TypeOfBug.ALL_BUGS, null);
        PdfExport pdfExport;
        try {
            pdfExport = new PdfExport(filename, ppd);
            pdfExport.write();
        } catch (PdfExportException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_menuExportPdfAllArchivedProjectsActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DialogChooseArchivedProject dial = new DialogChooseArchivedProject(this);
        if (dial.getReturnStatus() != DialogChooseArchivedProject.RET_OK) {
            return;
        }
        Project project = dial.getProject();
        String msg = I18N.getBundle("Really_delete_project_") + " : " + project.pro_name + " ?";
        int res = JOptionPane.showConfirmDialog(this, msg);
        if (res == JOptionPane.OK_OPTION) {
            try {
                ProjectFacade.delete(project);
            } catch (SqlTransactionException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuFileFindABugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileFindABugActionPerformed
        DialogFindBug dial = new DialogFindBug(this, true);
        if (dial.getReturnStatus() != DialogFindBug.RET_OK) {
            return;
        }
        Bug b = dial.getBug();
        Project p;
        try {
            p = ProjectDAO.readPk(b.pro_name);
            cbProjectModel.setSelectedItem(p);
            cbBugModel.setSelectedItem(b);
        } catch (SQLException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_menuFileFindABugActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSaveMessage;
    private javax.swing.JComboBox cbBug;
    private javax.swing.JComboBox cbCategory;
    private javax.swing.JComboBox cbPriority;
    private javax.swing.JComboBox cbProject;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JLabel lblBugId;
    private javax.swing.JLabel lblOpenedVersion;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JMenu menuExport;
    private javax.swing.JMenuItem menuExportAllBugs;
    private javax.swing.JMenuItem menuExportClosedBugs;
    private javax.swing.JMenuItem menuExportClosedBugsPriority;
    private javax.swing.JMenuItem menuExportClosedBugsVersion;
    private javax.swing.JMenuItem menuExportOpenedBugs;
    private javax.swing.JMenuItem menuExportOpenedBugsPriority;
    private javax.swing.JMenuItem menuExportOpenedBugsVersion;
    private javax.swing.JMenuItem menuExportPdfAllArchivedProjects;
    private javax.swing.JMenuItem menuExportPdfAllBugs;
    private javax.swing.JMenuItem menuExportPdfClosedBugs;
    private javax.swing.JMenuItem menuExportPdfClosedBugsPriority;
    private javax.swing.JMenuItem menuExportPdfClosedBugsVersion;
    private javax.swing.JMenuItem menuExportPdfOpenedBugs;
    private javax.swing.JMenuItem menuExportPdfOpenedBugsPriority;
    private javax.swing.JMenuItem menuExportPdfOpenedBugsVersion;
    private javax.swing.JMenuItem menuFileAddMessage;
    private javax.swing.JMenuItem menuFileChangeCategory;
    private javax.swing.JMenuItem menuFileChangePriority;
    private javax.swing.JMenuItem menuFileCloseBug;
    private javax.swing.JMenuItem menuFileDeleteBug;
    private javax.swing.JMenuItem menuFileDeleteCategory;
    private javax.swing.JMenuItem menuFileDeleteProject;
    private javax.swing.JMenuItem menuFileDeleteVersion;
    private javax.swing.JMenuItem menuFileFindABug;
    private javax.swing.JMenuItem menuFileNewBug;
    private javax.swing.JMenuItem menuFileNewCategory;
    private javax.swing.JMenuItem menuFileNewProject;
    private javax.swing.JMenuItem menuFileNewVersion;
    private javax.swing.JMenuItem menuFileQuit;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuHelpAbout;
    private javax.swing.JMenuItem menuHelpHelp;
    private javax.swing.JMenuItem menuHelpTutorials01;
    private javax.swing.JMenuItem menuHelpTutorials02;
    private javax.swing.JMenuItem menuHelpWeb;
    private javax.swing.JMenu menuMisc;
    private javax.swing.JMenuItem menuMiscChangeProjectName;
    private javax.swing.JMenuItem menuToolsArchiveProject;
    private javax.swing.JMenuItem menuToolsChooseLanguage;
    private javax.swing.JMenuItem menuToolsLoadProject;
    private javax.swing.JPanel panelMessages;
    private javax.swing.JScrollPane scrollPaneMessages;
    // End of variables declaration//GEN-END:variables
}
