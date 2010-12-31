require 'fileutils'
include FileUtils

BASEDIR = File.expand_path '.'
OUTDIR = File.join BASEDIR, 'bin'
SRCDIR = File.join BASEDIR, 'src'
SRCDIR_JAVA = File.join SRCDIR, 'java'
SRCDIR_RUBY = File.join SRCDIR, 'ruby'

task :default => :build_all

desc 'Build all classes'
task :build_all => [:verify_env, :build_from_java, :build_from_ruby]

desc 'Verify all dependencies are met'
task :verify_env do
  puts `jruby -v`
  require 'rubygems'
  require 'bitescript'
  mkdir_p OUTDIR
end

desc 'Build classes from Ruby source files'
task :build_from_ruby => [:verify_env, :build_from_java] do
  cd SRCDIR_RUBY
  Dir.glob('**/*.java').sort.each do |f|
    system "jruby -J-cp #{OUTDIR} #{f} #{OUTDIR} && echo 'compiled #{f}'"
  end
end

desc 'Build classes from Java source files'
task :build_from_java do
  cd SRCDIR_JAVA
  compile_log = File.join OUTDIR, 'compile.log'
  system "javac -classpath #{OUTDIR} -d #{OUTDIR} -g -Xstdout #{compile_log} #{Dir.glob('**/*.java').join(' ')}"
  rm compile_log
end

task :clean do
  rm_rf OUTDIR
end
