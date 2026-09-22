package gui;

import model.Etudiant;
import model.GestionEtudiants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FenetreEtudiant extends JFrame {

   
    // COULEURS
    private static final Color BLEU = new Color(25, 118, 210);
    private static final Color VERT = new Color(39, 174, 96);
    private static final Color ORANGE = new Color(239, 108, 0);
    private static final Color ROUGE = new Color(229, 57, 53);

    private static final Color GRIS_BORDURE =
            new Color(215, 220, 225);

    private static final Color GRIS_FOND =
            new Color(248, 249, 250);

    private static final Color GRIS_TEXTE =
            new Color(45, 55, 65);

    private static final Font FONT_NORMAL =
            new Font("Arial", Font.PLAIN, 13);

    private static final Font FONT_BOLD =
            new Font("Arial", Font.BOLD, 13);

    private static final Font FONT_TITRE_ZONE =
            new Font("Arial", Font.BOLD, 15);

   
    // FICHIER CSV
    private static final String FICHIER_DONNEES = "etudiants.csv";

    // GESTION DES DONNEES
    private GestionEtudiants gestion;

    
    // PANELS
    private JPanel panelPrincipal;
    private JPanel panelRecherche;
    private JPanel panelFormulaire;
    private JPanel panelTable;

    
    // FORMULAIRE

    private JTextField txtMatricule;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtDateNaissance;
    private JTextField txtEmail;
    private JTextField txtTelephone;

    private JComboBox<String> comboParcours;
    private JComboBox<String> comboAnnee;
    private JComboBox<String> comboStatut;

   
    // RECHERCHE
    private JTextField txtRechercheMatricule;
    private JTextField txtRechercheNom;

    private JComboBox<String> comboRechercheParcours;
    private JComboBox<String> comboRechercheStatut;

    
    // TABLE
    private JTable tableEtudiants;
    private DefaultTableModel modeleTable;

    // PAGINATION
    private JPanel panelPagination;

    private JLabel lblTotal;
    private JLabel lblAffichage;
    private JLabel lblPage;

    private JButton btnPremierePage;
    private JButton btnPagePrecedente;
    private JButton btnPageSuivante;
    private JButton btnDernierePage;

    private JComboBox<Integer> comboTaillePage;

    private int pageActuelle = 1;
    private int taillePage = 10;

    
    // BOUTONS
    private JButton btnRechercher;
    private JButton btnReinitialiser;

    private JButton btnNouveau;
    private JButton btnEnregistrer;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnAnnuler;


    // CONSTRUCTEUR
    public FenetreEtudiant() {

        gestion = new GestionEtudiants();

        // Chargement automatique des données
        chargerEtudiants();

        configurerFenetre();

        creerInterface();

        afficherEtudiants();

        // Sauvegarde automatique à la fermeture
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                sauvegarderEtudiants();
            }
        });

        setVisible(true);
    }

    
    // CONFIGURATION FENETRE
    private void configurerFenetre() {

        setTitle("Gestion des Étudiants");

        setSize(1350, 800);

        setMinimumSize(new Dimension(1150, 700));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception ignored) {
        }
    }

    
    // INTERFACE PRINCIPALE
    private void creerInterface() {

        panelPrincipal = new JPanel(new BorderLayout());

        panelPrincipal.setBackground(Color.WHITE);

        panelPrincipal.setBorder(
                new EmptyBorder(18, 18, 18, 18)
        );

        add(panelPrincipal);

        creerZoneRecherche();

        creerZoneCentrale();
    }


    // ZONE RECHERCHE
    private void creerZoneRecherche() {

        panelRecherche = new JPanel(new GridBagLayout());

        panelRecherche.setBackground(Color.WHITE);

        panelRecherche.setBorder(
                BorderFactory.createCompoundBorder(
                        new DashedBorder(VERT, 2, 5, 4),
                        new EmptyBorder(8, 12, 12, 12)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 7, 4, 7);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.anchor = GridBagConstraints.CENTER;

        // TITRE

        JLabel zoneRecherche =
                new JLabel(
                        "ZONE RECHERCHE",
                        SwingConstants.CENTER
                );

        zoneRecherche.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        zoneRecherche.setForeground(VERT);

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 10;

        gbc.weightx = 1;

        panelRecherche.add(zoneRecherche, gbc);

        // SOUS TITRE

        JLabel titre =
                new JLabel("⌕  Recherche d'étudiant");

        titre.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        titre.setForeground(
                new Color(35, 65, 105)
        );

        gbc.gridy = 1;

        gbc.anchor = GridBagConstraints.WEST;

        panelRecherche.add(titre, gbc);

        // LIGNE CRITERES

        gbc.gridy = 2;

        gbc.gridwidth = 1;

        gbc.anchor = GridBagConstraints.CENTER;

        // MATRICULE

        gbc.gridx = 0;
        gbc.weightx = 0;

        panelRecherche.add(
                labelRecherche("Matricule :"),
                gbc
        );

        txtRechercheMatricule = champ(11);

        gbc.gridx = 1;
        gbc.weightx = 0.8;

        panelRecherche.add(
                txtRechercheMatricule,
                gbc
        );

        // NOM

        gbc.gridx = 2;
        gbc.weightx = 0;

        panelRecherche.add(
                labelRecherche("Nom :"),
                gbc
        );

        txtRechercheNom = champ(11);

        gbc.gridx = 3;
        gbc.weightx = 0.8;

        panelRecherche.add(
                txtRechercheNom,
                gbc
        );

        // PARCOURS

        gbc.gridx = 4;
        gbc.weightx = 0;

        panelRecherche.add(
                labelRecherche("Parcours :"),
                gbc
        );

        comboRechercheParcours =
                new JComboBox<>(
                        new String[]{
                                "-- Tous --",
                                "Informatique",
                                "Paramedecine",
                                "Science sociale"
                        }
                );

        styliserCombo(comboRechercheParcours);

        gbc.gridx = 5;
        gbc.weightx = 0.9;

        panelRecherche.add(
                comboRechercheParcours,
                gbc
        );

        // STATUT

        gbc.gridx = 6;
        gbc.weightx = 0;

        panelRecherche.add(
                labelRecherche("Statut :"),
                gbc
        );

        comboRechercheStatut =
                new JComboBox<>(
                        new String[]{
                                "-- Tous --",
                                "Actif",
                                "Inactif"
                        }
                );

        styliserCombo(comboRechercheStatut);

        gbc.gridx = 7;
        gbc.weightx = 0.9;

        panelRecherche.add(
                comboRechercheStatut,
                gbc
        );

        // BOUTON RECHERCHER

        btnRechercher =
                bouton(
                        "⌕  Rechercher",
                        BLEU
                );

        gbc.gridx = 8;
        gbc.weightx = 0;

        panelRecherche.add(
                btnRechercher,
                gbc
        );

        // BOUTON reinitialisé

        btnReinitialiser =
                bouton(
                        "⟳  Réinitialiser",
                        Color.WHITE
                );

        btnReinitialiser.setForeground(GRIS_TEXTE);

        btnReinitialiser.setBorder(
                new LineBorder(
                        GRIS_BORDURE,
                        1,
                        true
                )
        );

        gbc.gridx = 9;

        panelRecherche.add(
                btnReinitialiser,
                gbc
        );

        // ACTIONS pour les boutons de recherche et réinitialisation

        btnRechercher.addActionListener(
                e -> rechercherEtudiants()
        );

        btnReinitialiser.addActionListener(
                e -> reinitialiserRecherche()
        );

        JPanel wrapper =
                new JPanel(new BorderLayout());

        wrapper.setBackground(Color.WHITE);

        wrapper.add(
                panelRecherche,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                wrapper,
                BorderLayout.NORTH
        );
    }

    
    // ZONE CENTRALE
    private void creerZoneCentrale() {

        JPanel centre =
                new JPanel(
                        new BorderLayout(15, 0)
                );

        centre.setBackground(Color.WHITE);

        centre.setBorder(
                new EmptyBorder(
                        12,
                        0,
                        0,
                        0
                )
        );

        
        // FORMULAIRE
        panelFormulaire =
                new JPanel(
                        new GridBagLayout()
                );

        panelFormulaire.setBackground(
                Color.WHITE
        );

        panelFormulaire.setBorder(
                BorderFactory.createCompoundBorder(
                        new DashedBorder(
                                BLEU,
                                2,
                                5,
                                4
                        ),
                        new EmptyBorder(
                                12,
                                12,
                                12,
                                12
                        )
                )
        );

        panelFormulaire.setPreferredSize(
                new Dimension(350, 0)
        );

        creerFormulaire();

        
        // TABLE
        panelTable =
                new JPanel(
                        new BorderLayout()
                );

        panelTable.setBackground(
                Color.WHITE
        );

        panelTable.setBorder(
                BorderFactory.createCompoundBorder(
                        new DashedBorder(
                                ORANGE,
                                2,
                                5,
                                4
                        ),
                        new EmptyBorder(
                                12,
                                12,
                                12,
                                12
                        )
                )
        );

        creerTable();

        
        // WRAPPER FORMULAIRE
        JPanel formulaireWrapper =
                new JPanel(
                        new BorderLayout()
                );

        formulaireWrapper.setBackground(
                Color.WHITE
        );

        formulaireWrapper.add(
                panelFormulaire,
                BorderLayout.CENTER
        );

        JLabel zoneFormulaire =
                new JLabel(
                        "ZONE FORMULAIRE",
                        SwingConstants.CENTER
                );

        zoneFormulaire.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        zoneFormulaire.setForeground(BLEU);

        zoneFormulaire.setBorder(
                new EmptyBorder(
                        7,
                        0,
                        0,
                        0
                )
        );

        formulaireWrapper.add(
                zoneFormulaire,
                BorderLayout.SOUTH
        );

        
        // WRAPPER TABLE
        JPanel tableWrapper =
                new JPanel(
                        new BorderLayout()
                );

        tableWrapper.setBackground(
                Color.WHITE
        );

        tableWrapper.add(
                panelTable,
                BorderLayout.CENTER
        );

        JLabel zoneTable =
                new JLabel(
                        "ZONE TABLE / JTABLE",
                        SwingConstants.CENTER
                );

        zoneTable.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        zoneTable.setForeground(ORANGE);

        zoneTable.setBorder(
                new EmptyBorder(
                        7,
                        0,
                        0,
                        0
                )
        );

        tableWrapper.add(
                zoneTable,
                BorderLayout.SOUTH
        );

        centre.add(
                formulaireWrapper,
                BorderLayout.WEST
        );

        centre.add(
                tableWrapper,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                centre,
                BorderLayout.CENTER
        );
    }

    // FORMULAIRE
    private void creerFormulaire() {

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(6, 7, 6, 7);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        JLabel titre =
                new JLabel(
                        "♟  Formulaire Étudiant"
                );

        titre.setFont(
                FONT_TITRE_ZONE
        );

        titre.setForeground(
                new Color(35, 105, 180)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 2;

        gbc.weightx = 1;

        panelFormulaire.add(
                titre,
                gbc
        );

        txtMatricule = champ(14);

        ajouterChamp(
                "Matricule :",
                txtMatricule,
                gbc,
                1
        );

        txtNom = champ(14);

        ajouterChamp(
                "Nom :",
                txtNom,
                gbc,
                2
        );

        txtPrenom = champ(14);

        ajouterChamp(
                "Prénom :",
                txtPrenom,
                gbc,
                3
        );

        txtDateNaissance = champ(14);

        txtDateNaissance.setToolTipText(
                "Format : jj/mm/aaaa"
        );

        ajouterChamp(
                "Date de naissance :",
                txtDateNaissance,
                gbc,
                4
        );

        txtEmail = champ(14);

        ajouterChamp(
                "Email :",
                txtEmail,
                gbc,
                5
        );

        txtTelephone = champ(14);

        ajouterChamp(
                "Téléphone :",
                txtTelephone,
                gbc,
                6
        );

        // PARCOURS

        comboParcours =
                new JComboBox<>(
                        new String[]{
                                "-- Sélectionner --",
                                "Informatique",
                                "Paramedecine",
                                "Science sociale"
                        }
                );

        styliserCombo(comboParcours);

        ajouterChamp(
                "Parcours :",
                comboParcours,
                gbc,
                7
        );

        // ANNEE

        comboAnnee =
                new JComboBox<>(
                        new String[]{
                                "2025 - 2026",
                                "2024 - 2025",
                                "2026 - 2027"
                        }
                );

        styliserCombo(comboAnnee);

        ajouterChamp(
                "Année universitaire :",
                comboAnnee,
                gbc,
                8
        );

        // STATUT

        comboStatut =
                new JComboBox<>(
                        new String[]{
                                "Actif",
                                "Inactif"
                        }
                );

        styliserCombo(comboStatut);

        ajouterChamp(
                "Statut :",
                comboStatut,
                gbc,
                9
        );

        
        // BOUTONS
        JPanel panelBoutons =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                8,
                                8
                        )
                );

        panelBoutons.setBackground(
                Color.WHITE
        );

        panelBoutons.setBorder(
                new EmptyBorder(
                        12,
                        0,
                        0,
                        0
                )
        );

        btnNouveau =
                bouton(
                        "＋  Nouveau",
                        VERT
                );

        btnEnregistrer =
                bouton(
                        "▣  Enregistrer",
                        BLEU
                );

        btnModifier =
                bouton(
                        "✎  Modifier",
                        new Color(
                                245,
                                166,
                                35
                        )
                );

        btnSupprimer =
                bouton(
                        "▣  Supprimer",
                        ROUGE
                );

        btnAnnuler =
                bouton(
                        "⊗  Annuler",
                        Color.WHITE
                );

        btnAnnuler.setForeground(
                GRIS_TEXTE
        );

        btnAnnuler.setBorder(
                new LineBorder(
                        GRIS_BORDURE,
                        1,
                        true
                )
        );

        panelBoutons.add(btnNouveau);
        panelBoutons.add(btnEnregistrer);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnAnnuler);

        // Bouton vide pour garder la grille équilibrée
        panelBoutons.add(new JLabel());

        gbc.gridx = 0;
        gbc.gridy = 10;

        gbc.gridwidth = 2;

        gbc.weightx = 1;

        gbc.weighty = 1;

        gbc.anchor =
                GridBagConstraints.SOUTH;

        panelFormulaire.add(
                panelBoutons,
                gbc
        );

        // ACTIONS

        btnNouveau.addActionListener(
                e -> nouveauEtudiant()
        );

        btnEnregistrer.addActionListener(
                e -> enregistrerEtudiant()
        );

        btnModifier.addActionListener(
                e -> modifierEtudiant()
        );

        btnSupprimer.addActionListener(
                e -> supprimerEtudiant()
        );

        btnAnnuler.addActionListener(
                e -> viderFormulaire()
        );
    }

    
    // AJOUTER CHAMP
    private void ajouterChamp(
            String texte,
            JComponent composant,
            GridBagConstraints gbc,
            int ligne
    ) {

        JLabel label =
                new JLabel(texte);

        label.setFont(FONT_NORMAL);

        label.setForeground(
                GRIS_TEXTE
        );

        gbc.gridx = 0;
        gbc.gridy = ligne;

        gbc.gridwidth = 1;

        gbc.weightx = 0;

        gbc.weighty = 0;

        gbc.anchor =
                GridBagConstraints.WEST;

        panelFormulaire.add(
                label,
                gbc
        );

        gbc.gridx = 1;

        gbc.weightx = 1;

        gbc.anchor =
                GridBagConstraints.CENTER;

        panelFormulaire.add(
                composant,
                gbc
        );
    }

    
    // TABLE
    private void creerTable() {

        JLabel titre =
                new JLabel(
                        "▤  Liste des Étudiants"
                );

        titre.setFont(
                FONT_TITRE_ZONE
        );

        titre.setForeground(
                ORANGE
        );

        titre.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        10,
                        0
                )
        );

        panelTable.add(
                titre,
                BorderLayout.NORTH
        );

        String[] colonnes = {

                "Matricule",
                "Nom",
                "Prénom",
                "Date naissance",
                "Email",
                "Parcours",
                "Année Univ.",
                "Statut"
        };

        modeleTable =
                new DefaultTableModel(
                        colonnes,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };

        tableEtudiants =
                new JTable(modeleTable);

        
        // TRI AUTOMATIQUE
        tableEtudiants.setAutoCreateRowSorter(true);

        tableEtudiants.setRowHeight(40);

        tableEtudiants.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        tableEtudiants.setForeground(
                new Color(35, 45, 60)
        );

        tableEtudiants.setGridColor(
                new Color(225, 229, 234)
        );

        tableEtudiants.setShowVerticalLines(true);

        tableEtudiants.setShowHorizontalLines(true);

        tableEtudiants.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tableEtudiants.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        tableEtudiants.setIntercellSpacing(
                new Dimension(1, 1)
        );

        tableEtudiants.setSelectionBackground(
                new Color(225, 238, 252)
        );

        tableEtudiants.setSelectionForeground(
                Color.DARK_GRAY
        );

        
        // HEADER

        JTableHeader header =
                tableEtudiants.getTableHeader();

        header.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        header.setForeground(
                new Color(30, 55, 90)
        );

        header.setBackground(
                new Color(245, 247, 249)
        );

        header.setPreferredSize(
                new Dimension(
                        header.getWidth(),
                        42
                )
        );

        header.setReorderingAllowed(false);

        
        // LARGEUR COLONNES

        int[] widths = {

                100,
                110,
                100,
                120,
                170,
                125,
                100,
                85
        };

        for (
                int i = 0;
                i < widths.length;
                i++
        ) {

            tableEtudiants
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(
                            widths[i]
                    );
        }

        
        // RENDU CELLULE
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();

        renderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        renderer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        for (
                int i = 0;
                i < tableEtudiants.getColumnCount();
                i++
        ) {

            tableEtudiants
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            renderer
                    );
        }

        // Renderer statut

        tableEtudiants
                .getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        new StatutRenderer()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tableEtudiants
                );

        scrollPane.setBorder(
                new LineBorder(
                        GRIS_BORDURE,
                        1
                )
        );

        scrollPane.getViewport()
                .setBackground(Color.WHITE);

        panelTable.add(
                scrollPane,
                BorderLayout.CENTER
        );

        creerPagination();

        
        // CLIC SUR UNE LIGNE
        tableEtudiants.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        remplirFormulaireDepuisTable();
                    }
                }
        );
    }


    // PAGINATION

    private void creerPagination() {

        panelPagination =
                new JPanel(
                        new BorderLayout()
                );

        panelPagination.setBackground(
                Color.WHITE
        );

        panelPagination.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );

        // TOTAL

        lblTotal =
                new JLabel(
                        "Total : 0 étudiants"
                );

        lblTotal.setFont(FONT_BOLD);

        lblTotal.setForeground(
                new Color(70, 80, 90)
        );

        JPanel gauche =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                4,
                                0
                        )
                );

        gauche.setBackground(
                Color.WHITE
        );

        gauche.add(lblTotal);

        // PAGINATION CENTRE

        JPanel centre =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                4,
                                0
                        )
                );

        centre.setBackground(
                Color.WHITE
        );

        btnPremierePage =
                petitBouton("⏮");

        btnPagePrecedente =
                petitBouton("◀");

        btnPageSuivante =
                petitBouton("▶");

        btnDernierePage =
                petitBouton("⏭");

        centre.add(btnPremierePage);

        centre.add(btnPagePrecedente);

        lblPage =
                new JLabel(
                        "1",
                        SwingConstants.CENTER
                );

        lblPage.setOpaque(true);

        lblPage.setBackground(BLEU);

        lblPage.setForeground(Color.WHITE);

        lblPage.setFont(FONT_BOLD);

        lblPage.setPreferredSize(
                new Dimension(35, 32)
        );

        centre.add(lblPage);

        centre.add(btnPageSuivante);

        centre.add(btnDernierePage);

        // DROITE

        JPanel droite =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                6,
                                0
                        )
                );

        droite.setBackground(
                Color.WHITE
        );

        lblAffichage =
                new JLabel(
                        "Affichage 0 - 0 sur 0"
                );

        lblAffichage.setFont(
                FONT_NORMAL
        );

        comboTaillePage =
                new JComboBox<>(
                        new Integer[]{
                                5,
                                10,
                                20,
                                50
                        }
                );

        comboTaillePage.setSelectedItem(
                taillePage
        );

        comboTaillePage.setFont(
                FONT_NORMAL
        );

        comboTaillePage.setPreferredSize(
                new Dimension(65, 32)
        );

        droite.add(lblAffichage);

        droite.add(comboTaillePage);

        panelPagination.add(
                gauche,
                BorderLayout.WEST
        );

        panelPagination.add(
                centre,
                BorderLayout.CENTER
        );

        panelPagination.add(
                droite,
                BorderLayout.EAST
        );

        // ACTIONS

        btnPremierePage.addActionListener(
                e -> {

                    pageActuelle = 1;

                    afficherPage();
                }
        );

        btnPagePrecedente.addActionListener(
                e -> {

                    if (pageActuelle > 1) {

                        pageActuelle--;

                        afficherPage();
                    }
                }
        );

        btnPageSuivante.addActionListener(
                e -> {

                    int totalPages =
                            calculerNombrePages();

                    if (
                            pageActuelle
                                    < totalPages
                    ) {

                        pageActuelle++;

                        afficherPage();
                    }
                }
        );

        btnDernierePage.addActionListener(
                e -> {

                    pageActuelle =
                            calculerNombrePages();

                    afficherPage();
                }
        );

        comboTaillePage.addActionListener(
                e -> {

                    if (
                            comboTaillePage
                                    .getSelectedItem()
                                    != null
                    ) {

                        taillePage =
                                (Integer)
                                        comboTaillePage
                                                .getSelectedItem();

                        pageActuelle = 1;

                        afficherPage();
                    }
                }
        );

        panelTable.add(
                panelPagination,
                BorderLayout.SOUTH
        );
    }

    // =========================================================
    // NOMBRE DE PAGES
    // =========================================================

    private int calculerNombrePages() {

        return Math.max(
                1,
                (int) Math.ceil(
                        (double)
                                gestion
                                        .getEtudiants()
                                        .size()
                                / taillePage
                )
        );
    }

    
    // PETIT BOUTON PAGINATION
    private JButton petitBouton(String texte) {

        JButton b =
                new JButton(texte);

        b.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        b.setFocusPainted(false);

        b.setBackground(Color.WHITE);

        b.setForeground(
                GRIS_TEXTE
        );

        b.setBorder(
                new LineBorder(
                        GRIS_BORDURE,
                        1,
                        true
                )
        );

        b.setPreferredSize(
                new Dimension(
                        40,
                        32
                )
        );

        b.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return b;
    }

    
    // STYLE DES CHAMPS

    private JTextField champ(int colonnes) {

        JTextField field =
                new JTextField(colonnes);

        field.setFont(
                FONT_NORMAL
        );

        field.setBackground(
                Color.WHITE
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(
                                        205,
                                        212,
                                        220
                                ),
                                1,
                                true
                        ),
                        new EmptyBorder(
                                6,
                                8,
                                6,
                                8
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(
                        155,
                        34
                )
        );

        return field;
    }

    
    // LABEL RECHERCHE
    private JLabel labelRecherche(
            String texte
    ) {

        JLabel label =
                new JLabel(texte);

        label.setFont(
                FONT_NORMAL
        );

        label.setForeground(
                GRIS_TEXTE
        );

        return label;
    }

    
    // STYLE COMBOBOX
    private void styliserCombo(
            JComboBox<String> combo
    ) {

        combo.setFont(
                FONT_NORMAL
        );

        combo.setBackground(
                Color.WHITE
        );

        combo.setPreferredSize(
                new Dimension(
                        155,
                        34
                )
        );
    }

    
    // BOUTONS
    private JButton bouton(
            String texte,
            Color fond
    ) {

        JButton button =
                new JButton(texte);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setOpaque(true);

        button.setBackground(fond);

        button.setForeground(
                fond.equals(Color.WHITE)
                        ? GRIS_TEXTE
                        : Color.WHITE
        );

        button.setBorder(
                new LineBorder(
                        fond.equals(Color.WHITE)
                                ? GRIS_BORDURE
                                : fond,
                        1,
                        true
                )
        );

        
        // BOUTONS grandes                                      
        button.setPreferredSize(
                new Dimension(
                        125,
                        42
                )
        );

        button.setMinimumSize(
                new Dimension(
                        115,
                        40
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    
    // BORDURE POINTILLES
    private static class DashedBorder
            extends LineBorder {

        private final int dash;
        private final int gap;

        DashedBorder(
                Color color,
                int thickness,
                int dash,
                int gap
        ) {

            super(
                    color,
                    thickness,
                    true
            );

            this.dash = dash;
            this.gap = gap;
        }

        @Override
        public void paintBorder(
                Component c,
                Graphics g,
                int x,
                int y,
                int width,
                int height
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setColor(lineColor);

            g2.setStroke(
                    new BasicStroke(
                            thickness,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10,
                            new float[]{
                                    dash,
                                    gap
                            },
                            0
                    )
            );

            g2.drawRoundRect(
                    x + thickness / 2,
                    y + thickness / 2,
                    width - thickness,
                    height - thickness,
                    7,
                    7
            );

            g2.dispose();
        }
    }

    
    // RENDERER STATUT
    private static class StatutRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            JLabel label =
                    (JLabel)
                            super
                                    .getTableCellRendererComponent(
                                            table,
                                            value,
                                            isSelected,
                                            hasFocus,
                                            row,
                                            column
                                    );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            label.setOpaque(true);

            if (
                    "Actif".equalsIgnoreCase(
                            String.valueOf(value)
                    )
            ) {

                label.setForeground(
                        new Color(
                                35,
                                130,
                                70
                        )
                );

                label.setBackground(
                        new Color(
                                225,
                                245,
                                231
                        )
                );

            } else {

                label.setForeground(
                        new Color(
                                180,
                                55,
                                55
                        )
                );

                label.setBackground(
                        new Color(
                                252,
                                230,
                                230
                        )
                );
            }

            return label;
        }
    }

   
    // AFFICHER ETUDIANTS
    private void afficherEtudiants() {

        pageActuelle = 1;

        afficherPage();
    }


    // AFFICHER PAGE
    private void afficherPage() {

        modeleTable.setRowCount(0);

        int total =
                gestion
                        .getEtudiants()
                        .size();

        int totalPages =
                calculerNombrePages();

        if (pageActuelle < 1) {

            pageActuelle = 1;
        }

        if (pageActuelle > totalPages) {

            pageActuelle =
                    totalPages;
        }

        int debut =
                (pageActuelle - 1)
                        * taillePage;

        int fin =
                Math.min(
                        debut + taillePage,
                        total
                );

        for (
                int i = debut;
                i < fin;
                i++
        ) {

            Etudiant etudiant =
                    gestion
                            .getEtudiants()
                            .get(i);

            modeleTable.addRow(
                    new Object[]{

                            etudiant.getMatricule(),
                            etudiant.getNom(),
                            etudiant.getPrenom(),
                            etudiant.getDateNaissance(),
                            etudiant.getEmail(),
                            etudiant.getParcours(),
                            etudiant.getAnneeUniversitaire(),
                            etudiant.getStatut()
                    }
            );
        }

        if (lblTotal != null) {

            lblTotal.setText(
                    "Total : "
                            + total
                            + " étudiants"
            );

            int debutAffichage =
                    total == 0
                            ? 0
                            : debut + 1;

            lblAffichage.setText(
                    "Affichage "
                            + debutAffichage
                            + " - "
                            + fin
                            + " sur "
                            + total
            );

            lblPage.setText(
                    String.valueOf(
                            pageActuelle
                    )
            );

            btnPremierePage.setEnabled(
                    pageActuelle > 1
            );

            btnPagePrecedente.setEnabled(
                    pageActuelle > 1
            );

            btnPageSuivante.setEnabled(
                    pageActuelle < totalPages
            );

            btnDernierePage.setEnabled(
                    pageActuelle < totalPages
            );
        }
    }

    
    // DONNEES DE TEST
    private void initialiserDonnees() {

        gestion.ajouter(
                new Etudiant(
                        "ETU001",
                        "RAKOTO",
                        "ANDRY",
                        "12/05/2002",
                        "andry@gmail.com",
                        "03441344555",
                        "Informatique",
                        "2025-2026",
                        "Actif"
                )
        );

        gestion.ajouter(
                new Etudiant(
                        "ETU002",
                        "RAVAO",
                        "ANDY",
                        "01/03/2005",
                        "andy@gmail.com",
                        "03455644555",
                        "Paramedecine",
                        "2025-2026",
                        "Actif"
                )
        );

        gestion.ajouter(
                new Etudiant(
                        "ETU003",
                        "RASOA",
                        "BERA",
                        "11/09/2007",
                        "bera@gmail.com",
                        "03441357775",
                        "Science sociale",
                        "2025-2026",
                        "Actif"
                )
        );
    }

    
    // RECHERCHE
    private void rechercherEtudiants() {

        String matricule =
                txtRechercheMatricule
                        .getText()
                        .trim();

        String nom =
                txtRechercheNom
                        .getText()
                        .trim();

        String parcours =
                comboRechercheParcours
                        .getSelectedItem()
                        .toString();

        String statut =
                comboRechercheStatut
                        .getSelectedItem()
                        .toString();

        java.util.ArrayList<Etudiant>
                resultats =
                gestion.rechercher(
                        matricule,
                        nom,
                        parcours,
                        statut
                );

        modeleTable.setRowCount(0);

        for (
                Etudiant etudiant :
                resultats
        ) {

            modeleTable.addRow(
                    new Object[]{

                            etudiant.getMatricule(),
                            etudiant.getNom(),
                            etudiant.getPrenom(),
                            etudiant.getDateNaissance(),
                            etudiant.getEmail(),
                            etudiant.getParcours(),
                            etudiant.getAnneeUniversitaire(),
                            etudiant.getStatut()
                    }
            );
        }

        int nombre =
                resultats.size();

        lblTotal.setText(
                "Total : "
                        + nombre
                        + " étudiants"
        );

        lblAffichage.setText(
                "Affichage "
                        + (nombre == 0
                        ? 0
                        : 1)
                        + " - "
                        + nombre
                        + " sur "
                        + nombre
        );

        pageActuelle = 1;

        lblPage.setText("1");

        btnPremierePage.setEnabled(false);
        btnPagePrecedente.setEnabled(false);
        btnPageSuivante.setEnabled(false);
        btnDernierePage.setEnabled(false);
    }

    // REINITIALISER RECHERCHE
    private void reinitialiserRecherche() {

        txtRechercheMatricule.setText("");

        txtRechercheNom.setText("");

        comboRechercheParcours
                .setSelectedIndex(0);

        comboRechercheStatut
                .setSelectedIndex(0);

        afficherEtudiants();
    }

    
    // NOUVEAU
    private void nouveauEtudiant() {

        viderFormulaire();

        txtMatricule.requestFocus();
    }

    
    // ENREGISTRER
    private void enregistrerEtudiant() {

        if (!verifierChamps()) {

            return;
        }

        String matricule =
                txtMatricule
                        .getText()
                        .trim();

        // Vérification matricule unique

        for (
                Etudiant e :
                gestion.getEtudiants()
        ) {

            if (
                    e.getMatricule()
                            .equalsIgnoreCase(
                                    matricule
                            )
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ce matricule existe déjà.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        Etudiant etudiant =
                recupererEtudiantDuFormulaire();

        gestion.ajouter(etudiant);

        // Sauvegarde immédiate

        sauvegarderEtudiants();

        afficherEtudiants();

        JOptionPane.showMessageDialog(
                this,
                "Étudiant ajouté avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );

        viderFormulaire();
    }

    
    // MODIFIER
    private void modifierEtudiant() {

        if (!verifierChamps()) {

            return;
        }

        String matricule =
                txtMatricule
                        .getText()
                        .trim();

        Etudiant etudiant =
                recupererEtudiantDuFormulaire();

        boolean resultat =
                gestion.modifier(
                        matricule,
                        etudiant
                );

        if (resultat) {

            // Sauvegarde immédiate

            sauvegarderEtudiants();

            afficherEtudiants();

            JOptionPane.showMessageDialog(
                    this,
                    "Étudiant modifié avec succès.",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );

            viderFormulaire();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Étudiant introuvable.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

   
    // SUPPRIMER
    private void supprimerEtudiant() {

        String matricule =
                txtMatricule
                        .getText()
                        .trim();

        if (matricule.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sélectionnez un étudiant.",
                    "Attention",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Voulez-vous supprimer l'étudiant "
                                + matricule
                                + " ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirmation
                        == JOptionPane.YES_OPTION
        ) {

            boolean resultat =
                    gestion.supprimer(
                            matricule
                    );

            if (resultat) {

                // Sauvegarde immédiate

                sauvegarderEtudiants();

                afficherEtudiants();

                JOptionPane.showMessageDialog(
                        this,
                        "Étudiant supprimé avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

                viderFormulaire();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Étudiant introuvable.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    
    // TABLE → FORMULAIRE
    private void remplirFormulaireDepuisTable() {

        int ligneVue =
                tableEtudiants
                        .getSelectedRow();

        if (ligneVue == -1) {

            return;
        }

        // IMPORTANT :
        // Permet de fonctionner correctement avec le tri.

        int ligne =
                tableEtudiants
                        .convertRowIndexToModel(
                                ligneVue
                        );

        txtMatricule.setText(
                modeleTable
                        .getValueAt(ligne, 0)
                        .toString()
        );

        txtNom.setText(
                modeleTable
                        .getValueAt(ligne, 1)
                        .toString()
        );

        txtPrenom.setText(
                modeleTable
                        .getValueAt(ligne, 2)
                        .toString()
        );

        txtDateNaissance.setText(
                modeleTable
                        .getValueAt(ligne, 3)
                        .toString()
        );

        txtEmail.setText(
                modeleTable
                        .getValueAt(ligne, 4)
                        .toString()
        );

        // Récupérer le téléphone
        // depuis l'objet Etudiant

        String matricule =
                modeleTable
                        .getValueAt(ligne, 0)
                        .toString();

        for (
                Etudiant e :
                gestion.getEtudiants()
        ) {

            if (
                    e.getMatricule()
                            .equalsIgnoreCase(
                                    matricule
                            )
            ) {

                txtTelephone.setText(
                        e.getTelephone()
                );

                break;
            }
        }

        comboParcours.setSelectedItem(
                modeleTable
                        .getValueAt(ligne, 5)
        );

        comboAnnee.setSelectedItem(
                modeleTable
                        .getValueAt(ligne, 6)
        );

        // CORRECTION :
        // Le statut est colonne 7,
        // pas colonne 8.

        comboStatut.setSelectedItem(
                modeleTable
                        .getValueAt(ligne, 7)
        );
    }

    // =========================================================
    // FORMULAIRE → OBJET ETUDIANT
    // =========================================================

    private Etudiant recupererEtudiantDuFormulaire() {

        return new Etudiant(

                txtMatricule
                        .getText()
                        .trim(),

                txtNom
                        .getText()
                        .trim(),

                txtPrenom
                        .getText()
                        .trim(),

                txtDateNaissance
                        .getText()
                        .trim(),

                txtEmail
                        .getText()
                        .trim(),

                txtTelephone
                        .getText()
                        .trim(),

                comboParcours
                        .getSelectedItem()
                        .toString(),

                comboAnnee
                        .getSelectedItem()
                        .toString(),

                comboStatut
                        .getSelectedItem()
                        .toString()
        );
    }

    // =========================================================
    // VERIFICATION DES CHAMPS
    // =========================================================

    private boolean verifierChamps() {

        // MATRICULE

        if (
                txtMatricule
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir le matricule.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtMatricule.requestFocus();

            return false;
        }

        // NOM

        if (
                txtNom
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir le nom.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtNom.requestFocus();

            return false;
        }

        // PRENOM

        if (
                txtPrenom
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir le prénom.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtPrenom.requestFocus();

            return false;
        }

        // DATE

        if (
                txtDateNaissance
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir la date de naissance.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDateNaissance.requestFocus();

            return false;
        }

        String date =
                txtDateNaissance
                        .getText()
                        .trim();

        if (
                !date.matches(
                        "\\d{2}/\\d{2}/\\d{4}"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "La date doit respecter le format jj/mm/aaaa.\n"
                            + "Exemple : 12/05/2002",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDateNaissance.requestFocus();

            return false;
        }

        // EMAIL

        if (
                txtEmail
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir l'email.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtEmail.requestFocus();

            return false;
        }

        String email =
                txtEmail
                        .getText()
                        .trim();

        if (
                !email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir une adresse email valide.\n"
                            + "Exemple : exemple@gmail.com",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtEmail.requestFocus();

            return false;
        }

        // TELEPHONE

        if (
                txtTelephone
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir le téléphone.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtTelephone.requestFocus();

            return false;
        }

        String telephone =
                txtTelephone
                        .getText()
                        .trim();

        if (
                !telephone.matches(
                        "[0-9]{10}"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Le numéro de téléphone doit contenir exactement 10 chiffres.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            txtTelephone.requestFocus();

            return false;
        }

        // PARCOURS

        if (
                comboParcours
                        .getSelectedIndex()
                        == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez sélectionner un parcours.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE
            );

            comboParcours.requestFocus();

            return false;
        }

        return true;
    }

    // =========================================================
    // VIDER FORMULAIRE
    // =========================================================

    private void viderFormulaire() {

        txtMatricule.setText("");

        txtNom.setText("");

        txtPrenom.setText("");

        txtDateNaissance.setText("");

        txtEmail.setText("");

        txtTelephone.setText("");

        comboParcours.setSelectedIndex(0);

        comboAnnee.setSelectedIndex(0);

        comboStatut.setSelectedIndex(0);

        tableEtudiants.clearSelection();
    }

    // =========================================================
    // SAUVEGARDE CSV
    // =========================================================

    private void sauvegarderEtudiants() {

        File fichier =
                new File(
                        FICHIER_DONNEES
                );

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(
                                        fichier
                                )
                        )
        ) {

            // EN-TETE

            writer.write(
                    "matricule;nom;prenom;dateNaissance;email;telephone;parcours;anneeUniversitaire;statut"
            );

            writer.newLine();

            // DONNEES

            for (
                    Etudiant etudiant :
                    gestion.getEtudiants()
            ) {

                writer.write(

                        nettoyerCSV(
                                etudiant.getMatricule()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getNom()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getPrenom()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getDateNaissance()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getEmail()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getTelephone()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getParcours()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant
                                        .getAnneeUniversitaire()
                        )

                        + ";"

                        + nettoyerCSV(
                                etudiant.getStatut()
                        )
                );

                writer.newLine();
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la sauvegarde des étudiants :\n"
                            + e.getMessage(),
                    "Erreur de sauvegarde",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // NETTOYAGE CSV
    // =========================================================

    private String nettoyerCSV(
            String valeur
    ) {

        if (valeur == null) {

            return "";
        }

        return valeur
                .replace(";", ",")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    // =========================================================
    // CHARGEMENT CSV
    // =========================================================

    private void chargerEtudiants() {

        File fichier =
                new File(
                        FICHIER_DONNEES
                );

        // Premier lancement

        if (!fichier.exists()) {

            initialiserDonnees();

            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(
                                        fichier
                                )
                        )
        ) {

            // Ignorer l'en-tête

            reader.readLine();

            String ligne;

            while (
                    (ligne = reader.readLine())
                            != null
            ) {

                if (
                        ligne.trim().isEmpty()
                ) {

                    continue;
                }

                String[] donnees =
                        ligne.split(
                                ";",
                                -1
                        );

                // 9 colonnes attendues

                if (
                        donnees.length != 9
                ) {

                    continue;
                }

                Etudiant etudiant =
                        new Etudiant(

                                donnees[0],
                                donnees[1],
                                donnees[2],
                                donnees[3],
                                donnees[4],
                                donnees[5],
                                donnees[6],
                                donnees[7],
                                donnees[8]
                        );

                gestion.ajouter(
                        etudiant
                );
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des étudiants :\n"
                            + e.getMessage(),
                    "Erreur de chargement",
                    JOptionPane.ERROR_MESSAGE
            );

            // En cas d'erreur,
            // utiliser les données de test

            initialiserDonnees();
        }
    }
}

