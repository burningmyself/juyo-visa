package io.znz.jsite.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 用于制作zip压缩包
 */
public class ZipUtil {
    private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 制作压缩包
     */
    public static void zip(OutputStream out, List<FileEntry> fileEntrys,String encoding) {
        new ZipUtil(out, fileEntrys, encoding);
    }

    /**
     * 制作压缩包
     */
    public static void zip(OutputStream out, List<FileEntry> fileEntrys) {
        new ZipUtil(out, fileEntrys, null);
    }

    /**
     * 创建Zipper对象
     * @param out 输出流
     */
    protected ZipUtil(OutputStream out, List<FileEntry> fileEntrys, String encoding) {
        Assert.notEmpty(fileEntrys);
        long begin = System.currentTimeMillis();
        log.debug("开始制作压缩包");
        try {
            try {
                zipOut = new ZipOutputStream(out);
                if (!StringUtils.isBlank(encoding)) {
                    log.debug("using encoding: {}", encoding);
                    zipOut.setEncoding(encoding);
                } else {
                    log.debug("using default encoding");
                }
                for (FileEntry fe : fileEntrys) {
                    zip(fe.getFile(), fe.getFilter(), fe.getZipEntry(), fe
                        .getPrefix());
                }
            } finally {
                zipOut.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("制作压缩包时，出现IO异常！", e);
        }
        long end = System.currentTimeMillis();
        log.info("制作压缩包成功。耗时：{}ms。", end - begin);
    }

    /**
     * 压缩文件
     * @param srcFile 源文件
     * @param pentry 父ZipEntry
     */
    private void zip(File srcFile, FilenameFilter filter, ZipEntry pentry, String prefix) throws IOException {
        ZipEntry entry;
        if (srcFile.isDirectory()) {
            if (pentry == null) {
                entry = new ZipEntry(srcFile.getName());
            } else {
                entry = new ZipEntry(pentry.getName() + "/" + srcFile.getName());
            }
            File[] files = srcFile.listFiles(filter);
            for (File f : files) {
                zip(f, filter, entry, prefix);
            }
        } else {
            if (pentry == null) {
                entry = new ZipEntry(prefix + srcFile.getName());
            } else {
                entry = new ZipEntry(pentry.getName() + "/" + prefix
                    + srcFile.getName());
            }
            FileInputStream in;
            try {
                log.debug("读取文件：{}", srcFile.getAbsolutePath());
                in = new FileInputStream(srcFile);
                try {
                    zipOut.putNextEntry(entry);
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        zipOut.write(buf, 0, len);
                    }
                    zipOut.closeEntry();
                } finally {
                    in.close();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("制作压缩包时，源文件不存在："
                    + srcFile.getAbsolutePath(), e);
            }
        }
    }

    private byte[] buf = new byte[1024];
    private ZipOutputStream zipOut;

    public static class FileEntry {
        private FilenameFilter filter;
        private String parent;
        private File file;
        private String prefix;

        public FileEntry(String parent, String prefix, File file,
                         FilenameFilter filter) {
            this.parent = parent;
            this.prefix = prefix;
            this.file = file;
            this.filter = filter;
        }

        public FileEntry(String parent, File file) {
            this.parent = parent;
            this.file = file;
        }

        public FileEntry(String parent, String prefix, File file) {
            this(parent, prefix, file, null);
        }

        public ZipEntry getZipEntry() {
            if (StringUtils.isBlank(parent)) {
                return null;
            } else {
                return new ZipEntry(parent);
            }
        }

        public FilenameFilter getFilter() {
            return filter;
        }

        public void setFilter(FilenameFilter filter) {
            this.filter = filter;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getPrefix() {
            if (prefix == null) {
                return "";
            } else {
                return prefix;
            }
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
