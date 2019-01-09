package supervcdstore.UI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JProgressBar;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import supervcdstore.utils.Util;
import supervcdstore.utils.WindowsCenter;
import supervcdstore.utils.MusicPlayer;

public class MusicPlayerFrame extends JFrame {

    long totalTime = 0;
    private DefaultListModel<String> defaultListModel = null;
    private MusicPlayer player = null;
    private File musicFile = null;
    private JButton playButton, stopButton;
    private JButton add, delete;
    public JPanel jp2, jp3, jp4;
    private JList jl;
    JLabel jl1, jl2, sj1, sj2;
    JTextField jt1, jt2;
    JButton queding, xiugai;
    private JButton closeButton;
    private JButton pauseButton;
    private JProgressBar progressBar;
    private Map<String, String> musicMap = new HashMap<String, String>();

    public MusicPlayerFrame() {
        super("Music Player");
        musicFile = new File("music.txt");
        if (!musicFile.isFile()) {
            try {
                musicFile.createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        pauseButton = new JButton("暂停播放");
        playButton = new JButton("开始播放");
        stopButton = new JButton("停止播放");
        jp2 = new JPanel();
        jp2.add(playButton);
        jp2.add(pauseButton);
        jp2.add(stopButton);
        c.add(jp2);

        jp4 = new JPanel();
        sj1 = new JLabel();
        sj2 = new JLabel();
        jp4.add(sj1);

        progressBar = new JProgressBar();
        progressBar.setString("00：00/00：00");
        progressBar.setStringPainted(true);
        jp4.add(progressBar);
        jp4.add(sj2);
        c.add(jp4);
        // ///////////////////////////////////////
        defaultListModel = new DefaultListModel<String>();
        jl = new JList(defaultListModel);
        jl.setVisibleRowCount(5);
        jl.setFixedCellHeight(40);
        jl.setFixedCellWidth(265);
        try {
            initList();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(jl);
        c.add(scrollPane);
        add = new JButton("添加");
        delete = new JButton("删除");
        closeButton = new JButton("关闭");
        jp3 = new JPanel();
        jp3.add(add);
        jp3.add(delete);
        jp3.add(closeButton);
        c.add(jp3);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (player != null) {
                    player.closePlay();;
                }
            }
        });

        /**
         * 暂停事件
         */
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.stopPlay();
                pauseButton.setEnabled(false);
            }
        });
        /**
         * 关闭事件
         */

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player != null) {
                    player.closePlay();
                }
                close();
            }
        });

        /**
         * 开始播放事件
         */
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMuisc();
            }
        });
        /**
         * 停止播放事件
         */
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setString("00：00/00：00");
                player.closePlay();
                player = null;
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
            }
        });
        /**
         * 添加按钮事件
         */
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileChooser = new JFileChooser(); // 实例化文件选择器
                fileChooser
                        .setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 设置文件选择模式,此处为文件和目录均可
                fileChooser.setCurrentDirectory(new File(".")); // 设置文件选择器当前目录
                fileChooser
                        .setFileFilter(new javax.swing.filechooser.FileFilter() {
                            @Override
                            public boolean accept(File file) { // 可接受的文件类型
                                String name = file.getName().toLowerCase();
                                return name.endsWith(".mp3") || file.isDirectory();
                            }

                            @Override
                            public String getDescription() { // 文件描述
                                return "音乐文件(*.mp3)";
                            }
                        });
                if (fileChooser.showOpenDialog(MusicPlayerFrame.this) == JFileChooser.APPROVE_OPTION) { // 弹出文件选择器,并判断是否点击了打开按钮
                    String filePath = fileChooser.getSelectedFile()
                            .getAbsolutePath(); // 得到选择文件或目录的绝对路径
                    addMusic(filePath);
                }
            }
        });
        /**
         *
         * 删除按钮事件
         */
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String selectedValue = (String) jl.getSelectedValue();
                if (selectedValue != null) {
                    if (player != null
                            && ((String) musicMap.get(selectedValue))
                            .equals(player.getPath())) {
                        progressBar.setString("00：00/00：00");
                        player.closePlay();
                        player = null;
                        pauseButton.setEnabled(false);
                        stopButton.setEnabled(false);
                    }
                    defaultListModel.removeElement(selectedValue);
                    musicMap.remove(selectedValue);
                    writeList();
                } else {
                    //JOptionPane.showMessageDialog(null, "请选择文件");
                }
            }
        });
        /**
         * 列表点击事件
         */
        jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    playMuisc();
                }
                if (event.getButton() == 3) {
                    String selectedValue = (String) jl.getSelectedValue();
                    if (selectedValue != null) {
//						try {
//							WindowsCenter.Center(new Mp3InfoDialog(getThis(),
//									(String) musicMap.get(selectedValue)));
//						} catch (IOException | TagException
//								| ReadOnlyFileException | CannotReadException
//								| InvalidAudioFrameException e) {
//							// TODO Auto-generated catch block
//							JOptionPane.showMessageDialog(null, "文件信息错误", "提示",
//									JOptionPane.INFORMATION_MESSAGE);
//						}
                    } else {
                        JOptionPane.showMessageDialog(null, "请选择音乐", "提示",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        /**
         * 退出事件
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        setSize(349, 375);
    }

    public void setSelectedAndPlay(String path) {
        jl.setSelectedValue(path, true);
        playMuisc();
    }

    private void playMuisc() {
        String music = (String) jl.getSelectedValue();
        if (music != null) {
            if (player == null) {// 没有播放音乐
                try {
                    totalTime = Util.getTime(musicMap.get(music));
                    player = new MusicPlayer(musicMap.get(music), getThis());
                } catch (UnsupportedAudioFileException e) {
                    // TODO Auto-generated catch block
                    musicMap.remove(music);
                    defaultListModel.removeElement(music);
                    jl.updateUI();
                    JOptionPane.showMessageDialog(null, "音乐文件未找到");
                    //return;
                } catch (LineUnavailableException e1) {
                    // TODO Auto-generated catch block
                    musicMap.remove(music);
                    defaultListModel.removeElement(music);
                    jl.updateUI();
                    JOptionPane.showMessageDialog(null, "音乐文件未找到");
                    return;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    musicMap.remove(music);
                    defaultListModel.removeElement(music);
                    jl.updateUI();
                    JOptionPane.showMessageDialog(null, "音乐文件未找到");
                    return;
                }
            } else if (player.getPath() != musicMap.get(music)) {// 不是当前播放的音乐
                player.closePlay();
                try {
                    totalTime = Util.getTime(musicMap.get(music));
                    player = new MusicPlayer(musicMap.get(music), getThis());
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
                    musicMap.remove(music);
                    defaultListModel.removeElement(music);
                    jl.updateUI();
                    JOptionPane.showMessageDialog(null, "音乐文件未找到");
                }
            }
            // 开始播放
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            player.beginPlay();

        } else {
            //JOptionPane.showMessageDialog(null, "请选择音乐文件");
        }
    }

    /**
     * 读取音乐列表
     *
     * @throws IOException
     */
    private void initList() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(musicFile)));
        String temp = null;
        while ((temp = br.readLine()) != null) {
            String[] split = temp.split("=");
            musicMap.put(split[0], split[1]);
            defaultListModel.addElement(split[0]);
        }
        jl.updateUI();
    }

    /**
     * 将列表写入文件
     */
    private void writeList() {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(musicFile)));
            Set<String> keySet = musicMap.keySet();
            for (String s : keySet) {
                String value = musicMap.get(s);
                bw.write(s + "=" + value);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("保存错误");
        }

    }

    /**
     * 添加音乐
     *
     * @param path 音乐决定路径
     */
    public void addMusic(String path) {
        File f = new File(path);
        if (!musicMap.containsKey(f.getName())) {
            musicMap.put(f.getName(), path);
            defaultListModel.addElement(f.getName());
            writeList();
            jl.updateUI();
        } else {
            //JOptionPane.showMessageDialog(null, "此歌曲文件已经存在");
        }
    }

    public MusicPlayerFrame getThis() {
        return this;
    }

    public JFrame getFrame() {
        return this;
    }

    /**
     * 设置滚动条
     *
     * @param currentTime 当前时间
     */
    public void setProgressBar(Long currentTime) {
        String currentTimeString = Util.getTimeString(currentTime);
        String totalTimeString = Util.getTimeString(totalTime);
        progressBar.setString(currentTimeString + "/" + totalTimeString);
        progressBar.setValue((int) ((currentTime * 100 / totalTime)));
    }

    public void close() {
        this.setVisible(false);
    }

    public void open() {
        this.setVisible(false);
    }

    public static void main(String agrs[]) {
        MusicPlayerFrame s = new MusicPlayerFrame();
        s.setVisible(true);
    }

}
