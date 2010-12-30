$: << File.dirname(__FILE__)
require '../../lib/hotspot_evil'

import Java::HotspotEvilUtil::RuntimeProperties

builder = FileBuilder.build(__FILE__) do
  package 'hotspot.evil' do
    public_class 'Dereference', MagicAccessorImpl do      
      public_static_method :int8, [], byte, long do
        lload 0
        lsub_object_header_size
        lstore 0
        aload 1
        getfield java.lang.Byte, :value, byte
        ireturn
      end
      
      public_static_method :int16, [], short, long do
        lload 0
        lsub_object_header_size
        lstore 0
        aload 1
        getfield java.lang.Short, :value, short
        ireturn
      end
      
      public_static_method :int32, [], int, long do
        lload 0
        lsub_object_header_size
        lstore 0
        aload 1
        getfield java.lang.Integer, :value, int
        ireturn
      end
      
      public_static_method :int64, [], long, long do
        lload 0
        lsub_object_header_size
        lstore 0
        aload 1
        getfield java.lang.Long, :value, long
        lreturn
      end
    end
  end
end

outdir = ARGV.first || '.'

builder.generate do |filename, class_builder|
  require 'fileutils'
  FileUtils.mkdir_p(File.join(outdir, File.dirname(filename)))
  File.open(File.join(outdir, filename), 'wb') do |file|
    file.write(class_builder.generate)
  end
end

